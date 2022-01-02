import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {NewInternalOrderComponent} from './new-internal-order/new-internal-order.component';

@Component({
  selector: 'app-internal-orders-overview',
  templateUrl: './internal-orders-overview.component.html',
  styleUrls: ['./internal-orders-overview.component.css']
})
export class InternalOrdersOverviewComponent implements OnInit {
  constructor(private dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  createNewOrderClick(): void {
    this.dialog.open<NewInternalOrderComponent, any, any>(NewInternalOrderComponent, {
      minWidth: '40em',
      data: {},
      disableClose: true
    }).afterClosed().subscribe(result => {
      if (result) {
      }
    });
  }
}
