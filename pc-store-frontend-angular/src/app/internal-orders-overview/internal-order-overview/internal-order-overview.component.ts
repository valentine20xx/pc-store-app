import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {getSalutations, InternalOrderDTO} from '../../model/model';
import {isNotEmpty} from '../../utils/utils';

@Component({
  selector: 'app-internal-order-overview',
  templateUrl: './internal-order-overview.component.html',
  styleUrls: ['./internal-order-overview.component.css']
})
export class InternalOrderOverviewComponent implements OnInit {

  salutations = getSalutations();

  constructor(private dialog: MatDialog,
              private dialogRef: MatDialogRef<InternalOrderOverviewComponent, any>,
              @Inject(MAT_DIALOG_DATA) public internalOrderDTO: InternalOrderDTO) {
  }

  ngOnInit(): void {
    console.debug('internalOrderDTO:', this.internalOrderDTO);
  }

  isNotEmpty(str: string): boolean {
    return isNotEmpty(str);
  }
}
