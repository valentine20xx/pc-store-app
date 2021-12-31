import {Directive, ElementRef, Self} from '@angular/core';
import {NgControl} from '@angular/forms';

@Directive({
  selector: '[appAutoTrim]'
})
export class AutoTrimDirective {
  constructor(elementRef: ElementRef, @Self() ngControl: NgControl) {
    elementRef.nativeElement.onchange = (event: any) => {
      if (ngControl != null && ngControl.control != null) {
        ngControl.control.setValue(event.target.value.trim());
      }
    };
  }
}
