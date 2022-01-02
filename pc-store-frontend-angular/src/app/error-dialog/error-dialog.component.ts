import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-error-dialog',
  templateUrl: './error-dialog.component.html',
  styleUrls: ['./error-dialog.component.css']
})
export class ErrorDialogComponent implements OnInit {
  constructor(private dialogRef: MatDialogRef<any, ErrorDialogCloseButton>,
              @Inject(MAT_DIALOG_DATA) public inputData: ErrorDialogData) {
  }

  retryClick(): void {
    this.dialogRef.close(ErrorDialogCloseButton.RETRY);
  }

  cancelClick(): void {
    this.dialogRef.close(ErrorDialogCloseButton.CANCEL);
  }

  ngOnInit(): void {
  }
}

export interface ErrorDialogData {
  message: string;
  cancelable: boolean;
}

export enum ErrorDialogCloseButton {
  RETRY, CANCEL
}
