import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LongTextShortComponent } from './long-text-short.component';

describe('LongTextShortComponent', () => {
  let component: LongTextShortComponent;
  let fixture: ComponentFixture<LongTextShortComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LongTextShortComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LongTextShortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
