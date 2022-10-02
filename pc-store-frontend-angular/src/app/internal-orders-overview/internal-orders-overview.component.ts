import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {select, Store} from '@ngrx/store';
import {Subscription} from 'rxjs';
import {AppState} from '../app.module';
import {getInternalOrderStatuses, INTERNAL_ORDER_STATUSES, InternalOrderDTO, InternalOrderShortDTO} from '../model/model';
import {InternalOrdersService} from '../services/internal-orders.service';
import {loadInternalOrder, loadInternalOrders} from '../state/app.actions';
import {internalOrdersStateFeature, internalOrderStateFeature} from '../state/app.selectors';
import {ErrorOverlayService} from '../utils/error-overlay.service';
import {LoaderService} from '../utils/loader.service';
import {InternalOrderOverviewComponent} from './internal-order-overview/internal-order-overview.component';
import {NewInternalOrderComponent, NewInternalOrderOutput} from './new-internal-order/new-internal-order.component';


@Component({
  selector: 'app-internal-orders-overview',
  templateUrl: './internal-orders-overview.component.html',
  styleUrls: ['./internal-orders-overview.component.css']
})
export class InternalOrdersOverviewComponent implements OnInit, AfterViewInit, OnDestroy {
  displayedColumns: string[] = ['client', 'personalComputer', 'status', 'dateOfReceiving', 'actions'];
  internalOrderShortDataSource = new MatTableDataSource<InternalOrderShortDTO>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  subscriptions: Array<Subscription> = [];

  statuses = getInternalOrderStatuses();

  constructor(private store: Store<AppState>,
              private dialog: MatDialog,
              private loaderService: LoaderService,
              private internalOrdersService: InternalOrdersService,
              private errorOverlayService: ErrorOverlayService) {
  }

  ngOnInit(): void {
    this.subscriptions.push(
      this.store.pipe(select(internalOrdersStateFeature)).subscribe(value => {
          switch (value.status) {
            case 'SEND':
              this.loaderService.showSpinner();
              this.internalOrderShortDataSource.data = [];
              break;
            case 'RECEIVED':
              this.loaderService.hideSpinner();
              this.internalOrderShortDataSource.data = value.internalOrders;
              break;
            case 'FAILURE':
              this.loaderService.hideSpinner();
              this.internalOrderShortDataSource.data = [];
              this.errorOverlayService.showErrorOverlay('Unable to load internal orders', () => {
                this.store.dispatch(loadInternalOrders({status: INTERNAL_ORDER_STATUSES.OPEN}));
              });
              break;
          }
        }
      ),
      this.store.pipe(select(internalOrderStateFeature)).subscribe(value => {
        switch (value.status) {
          case 'SEND':
            this.loaderService.showSpinner();
            break;
          case 'RECEIVED':
            this.loaderService.hideSpinner();
            this.dialog.open<InternalOrderOverviewComponent, InternalOrderDTO, any>(InternalOrderOverviewComponent, {
              width: '50em',
              data: value.internalOrder,
              disableClose: true
            })
            break;
          case 'FAILURE':
            this.loaderService.hideSpinner();
            break;
        }
      })
    )

    this.store.dispatch(loadInternalOrders({status: INTERNAL_ORDER_STATUSES.OPEN}));
  }

  ngAfterViewInit() {
    this.internalOrderShortDataSource.paginator = this.paginator;
    this.internalOrderShortDataSource.sort = this.sort;
  }

  refreshClick(): void {
    this.store.dispatch(loadInternalOrders({status: INTERNAL_ORDER_STATUSES.OPEN}));
  }

  createNewOrderClick(): void {
    this.dialog.open<NewInternalOrderComponent, any, NewInternalOrderOutput>(NewInternalOrderComponent, {
      minWidth: '40em',
      data: {},
      disableClose: true
    }).afterClosed().subscribe(result => {
      if (result) {
        const formDate = new FormData();
        formDate.set('internal-order', JSON.stringify(result.newInternalOrder));

        result.documents.forEach(value => {
          if (value.file != null && value.id != null) {
            formDate.append(value.id, value.file);
          }
        })

        this.internalOrdersService.addInternalOrderMP(result.newInternalOrder, formDate).subscribe(value => {
          this.store.dispatch(loadInternalOrders({status: INTERNAL_ORDER_STATUSES.OPEN}));
        }, error => {
          console.error(error)
        });
      }
    });
  }

  internalOrderOverviewClick(id: string): void {
    this.store.dispatch(loadInternalOrder({id}));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(value => value.unsubscribe());
  }
}
