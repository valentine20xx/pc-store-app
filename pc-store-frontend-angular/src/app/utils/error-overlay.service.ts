import {Injectable} from '@angular/core';
import {Overlay, OverlayRef} from '@angular/cdk/overlay';
import {ErrorDialogCloseButton, ErrorDialogComponent, ErrorDialogData} from '../error-dialog/error-dialog.component';
import {MatDialog} from '@angular/material/dialog';

@Injectable({
  providedIn: 'root'
})
export class ErrorOverlayService {
  constructor(private overlay: Overlay, private dialog: MatDialog) {
  }

  private spinnerRef: OverlayRef = this.cdkSpinnerCreate();
  private isActive = false;

  private cdkSpinnerCreate() {
    return this.overlay.create({
      hasBackdrop: true,
      positionStrategy: this.overlay.position().global().centerHorizontally().centerVertically()
    });
  }

  showErrorOverlay(message: string, fx?: () => void, cancelable: boolean = false): void {
    if (this.isActive) {
      console.warn('ErrorOverlay was already showed');
    } else {
      this.dialog.open<ErrorDialogComponent, ErrorDialogData, ErrorDialogCloseButton>(ErrorDialogComponent, {
        width: '20em',
        disableClose: true,
        data: {
          message,
          cancelable
        }
      }).afterClosed().subscribe(value => {
        this.hideErrorOverlay();
        if (value === ErrorDialogCloseButton.RETRY && fx) {
          fx();
        }
      });

      this.isActive = true;
    }
  }

  private hideErrorOverlay() {
    if (this.isActive) {
      this.spinnerRef.detach();
      this.isActive = false;
    } else {
      console.warn('ErrorOverlay was already hidden');
    }
  }
}
