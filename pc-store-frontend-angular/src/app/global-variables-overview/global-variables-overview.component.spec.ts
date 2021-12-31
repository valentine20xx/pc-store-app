import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GlobalVariablesOverviewComponent } from './global-variables-overview.component';

describe('GlobalVariablesOverviewComponent', () => {
  let component: GlobalVariablesOverviewComponent;
  let fixture: ComponentFixture<GlobalVariablesOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GlobalVariablesOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GlobalVariablesOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
