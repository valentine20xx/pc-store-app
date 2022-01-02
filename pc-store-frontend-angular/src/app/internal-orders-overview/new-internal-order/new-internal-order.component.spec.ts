import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewInternalOrderComponent } from './new-internal-order.component';

describe('NewInternalOrderComponent', () => {
  let component: NewInternalOrderComponent;
  let fixture: ComponentFixture<NewInternalOrderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewInternalOrderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewInternalOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
