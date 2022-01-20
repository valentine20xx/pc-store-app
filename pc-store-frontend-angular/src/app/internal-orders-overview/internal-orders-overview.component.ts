import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {NewInternalOrderComponent} from './new-internal-order/new-internal-order.component';
import {getInternalOrderStatuses, InternalOrderShortDTO} from "../model/model";
import {Store} from "@ngrx/store";
import {skip, Subscription} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";
import {LoaderService} from "../utils/loader.service";
import {ErrorOverlayService} from "../utils/error-overlay.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AppState} from "../app.module";
import {loadInternalOrders} from "../state/app.actions";


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
              private errorOverlayService: ErrorOverlayService) {
  }

  ngOnInit(): void {
    this.one = this.store.select(state => state).pipe(skip(1)).subscribe(value => {
        console.log('one subscription=', value);
        this.internalOrderShortDataSource.data = value.internalOrdersState.internalOrders;
        value.internalOrdersState.loading ? this.loaderService.showSpinner() : this.loaderService.hideSpinner();
        return value.internalOrdersState.hasError ? this.errorOverlayService.showErrorOverlay('Unable to load internal orders', () => {
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
    this.dialog.open<NewInternalOrderComponent, any, InternalOrderShortDTO>(NewInternalOrderComponent, {
      minWidth: '40em',
      data: {},
      disableClose: true
    }).afterClosed().subscribe(result => {
      if (result) {
        this.internalOrderShortDataSource.data = [...this.internalOrderShortDataSource.data, result];
      }
    });
  }

  ngOnDestroy(): void {
    this.one?.unsubscribe();

  }
}
