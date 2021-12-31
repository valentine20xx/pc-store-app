import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewInternalOrdersComponent } from './new-internal-orders.component';

describe('NewInternalOrdersComponent', () => {
  let component: NewInternalOrdersComponent;
  let fixture: ComponentFixture<NewInternalOrdersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewInternalOrdersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewInternalOrdersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
