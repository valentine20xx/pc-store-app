import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {NewInternalOrdersComponent} from './new-internal-orders/new-internal-orders.component';

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
    this.dialog.open<NewInternalOrdersComponent, any, any>(NewInternalOrdersComponent, {
      minWidth: '40em',
      data: {},
      disableClose: true
    }).afterClosed().subscribe(result => {
      if (result) {
      }
    });
  }
}
