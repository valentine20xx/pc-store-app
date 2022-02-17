import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InternalOrderOverviewComponent } from './internal-order-overview.component';

describe('InternalOrderOverviewComponent', () => {
  let component: InternalOrderOverviewComponent;
  let fixture: ComponentFixture<InternalOrderOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InternalOrderOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InternalOrderOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
