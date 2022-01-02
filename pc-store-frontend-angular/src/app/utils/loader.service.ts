import {Injectable} from '@angular/core';
import {Overlay, OverlayRef} from '@angular/cdk/overlay';
import {ComponentPortal} from '@angular/cdk/portal';
import {MatSpinner} from "@angular/material/progress-spinner";

@Injectable({
  providedIn: 'root'
})
export class LoaderService {
  private spinnerRef: OverlayRef = this.cdkSpinnerCreate();
  private isActive = false;

  constructor(private overlay: Overlay) {
  }

  private cdkSpinnerCreate() {
    return this.overlay.create({
      hasBackdrop: true,
      positionStrategy: this.overlay.position().global().centerHorizontally().centerVertically()
    });
  }

  showSpinner() {
    if (this.isActive) {
      console.warn('Loader was already showed');
    } else {
      this.spinnerRef.attach(new ComponentPortal(MatSpinner));
      this.isActive = true;
    }
  }

  hideSpinner() {
    if (this.isActive) {
      this.spinnerRef.detach();
      this.isActive = false;
    } else {
      console.warn('Loader was already hidden');
    }
  }
}
