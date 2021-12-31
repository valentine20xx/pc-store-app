import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternalOrdersOverviewComponent } from './internal-orders-overview.component';

describe('InternalOrdersOverviewComponent', () => {
  let component: InternalOrdersOverviewComponent;
  let fixture: ComponentFixture<InternalOrdersOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InternalOrdersOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InternalOrdersOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
