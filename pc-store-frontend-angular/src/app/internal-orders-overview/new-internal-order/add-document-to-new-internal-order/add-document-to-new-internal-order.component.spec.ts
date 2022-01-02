import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDocumentToNewInternalOrderComponent } from './add-document-to-new-internal-order.component';

describe('AddDocumentToNewInternalOrderComponent', () => {
  let component: AddDocumentToNewInternalOrderComponent;
  let fixture: ComponentFixture<AddDocumentToNewInternalOrderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddDocumentToNewInternalOrderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDocumentToNewInternalOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
