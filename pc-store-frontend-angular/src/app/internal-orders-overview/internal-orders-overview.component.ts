import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {NewInternalOrderComponent, NewInternalOrderOutput} from './new-internal-order/new-internal-order.component';
import {getInternalOrderStatuses, InternalOrderShortDTO, NewInternalOrderMPDTO} from "../model/model";
import {select, Store} from "@ngrx/store";
import {skip, Subscription} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";
import {LoaderService} from "../utils/loader.service";
import {ErrorOverlayService} from "../utils/error-overlay.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AppState} from "../app.module";
import {loadInternalOrders} from "../state/app.actions";
import {loginStateFeature, internalOrdersStateFeature} from "../state/app.selectors";
import {InternalOrdersService} from "../services/internal-orders.service";


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

  one?: Subscription;
  statuses = getInternalOrderStatuses();

  constructor(private store: Store<AppState>,
              private dialog: MatDialog,
              private loaderService: LoaderService,
              private internalOrdersService: InternalOrdersService,
              private errorOverlayService: ErrorOverlayService) {
  }

  ngOnInit(): void {
    this.one = this.store.pipe(select(internalOrdersStateFeature), skip(1)).subscribe(value => {
        this.internalOrderShortDataSource.data = value.internalOrders;
        value.loading ? this.loaderService.showSpinner() : this.loaderService.hideSpinner();
        return value.hasError ? this.errorOverlayService.showErrorOverlay('Unable to load internal orders', () => {
          this.store.dispatch(loadInternalOrders());
        }) : null;
      }
    );

    this.store.dispatch(loadInternalOrders());
  }

  ngAfterViewInit() {
    this.internalOrderShortDataSource.paginator = this.paginator;
    this.internalOrderShortDataSource.sort = this.sort;
  }

  refreshClick(): void {
    this.store.dispatch(loadInternalOrders());
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
          if (value.file != null && value.id != null)
            formDate.append(value.id, value.file);
        })

        this.internalOrdersService.addInternalOrderMP(result.newInternalOrder, formDate).subscribe(value => {
          this.store.dispatch(loadInternalOrders());
        }, error => {
          console.error(error)
        });
      }
    });
  }

  ngOnDestroy(): void {
    this.one?.unsubscribe();
  }
}
