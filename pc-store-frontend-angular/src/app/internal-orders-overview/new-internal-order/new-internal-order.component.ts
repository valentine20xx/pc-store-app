import {Component, Inject, ViewChild} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {MatStepper} from '@angular/material/stepper';
import {MatTableDataSource} from '@angular/material/table';
import {Store} from '@ngrx/store';
import {AppState} from '../../app.module';
import {getSalutations, NewInternalOrderMPDTO} from '../../model/model';
import {addInternalOrder} from '../../state/app.actions';
import {generateId} from '../../utils/utils';
import {AddDocumentOutput, AddDocumentToNewInternalOrderComponent, Buttons} from './add-document-to-new-internal-order/add-document-to-new-internal-order.component';

@Component({
  selector: 'app-new-internal-order',
  templateUrl: './new-internal-order.component.html',
  styleUrls: ['./new-internal-order.component.css']
})
export class NewInternalOrderComponent {
  @ViewChild('stepper') stepper!: MatStepper;

  personalComputerForm = this.formBuilder.group({
    computerCase: ['', [Validators.required]],
    motherboard: ['', [Validators.required]],
    processor: ['', [Validators.required]],
    graphicsCard: ['', [Validators.required]],
    randomAccessMemory: ['', [Validators.required]],
    storageDevice: ['', [Validators.required]],
    powerSupplyUnit: ['', [Validators.required]]
  });

  clientDataForm = this.formBuilder.group({
    salutation: ['', [Validators.required]],
    name: ['', [Validators.required, Validators.pattern('^[A-Za-zÖÄÜöäüß\ \-]*$')]],
    surname: ['', [Validators.required, Validators.pattern('^[A-Za-zÖÄÜöäüß\ \-]*$')]],
    street: ['', [Validators.required, Validators.pattern('^[A-Za-zÖÄÜöäüß\ \-.&]*$')]],
    houseNumber: ['', [Validators.required, Validators.pattern('^([0-9A-Za-zÖÄÜöäüß])*$')]],
    zip: ['', [Validators.required, Validators.pattern('^(\\d{5})$')]],
    city: ['', [Validators.required, Validators.pattern('^[A-Za-zÖÄÜöäüß\ \-.&]*$')]],
    telephone: ['', Validators.pattern('^\\+{0,1}([0-9\ ])*$')],
    cellphone: ['', Validators.pattern('^\\+{0,1}([0-9\ ])*$')],
    email: ['', Validators.pattern('^.{1,}@.{1,}\\..{2,}$')]
  });

  documentsDisplayedColumns: string[] = ['name', 'note', 'actions'];
  documentsDataSource = new MatTableDataSource<Document>();

  salutations = getSalutations();

  constructor(private store: Store<AppState>,
              private formBuilder: FormBuilder,
              private dialog: MatDialog,
              private dialogRef: MatDialogRef<NewInternalOrderComponent, NewInternalOrderOutput>,
              @Inject(MAT_DIALOG_DATA) public input: any) {

    this.documentsDataSource.data = [];
  }

  stepperNextClick(): void {
    this.stepper.next();
  }

  stepperPreviousClick(): void {
    this.stepper.previous()
  }

  newInternalOrderCreate(): void {
    const newInternalOrder = {
      clientData: {
        salutation: this.clientDataForm.controls['salutation'].value,
        name: this.clientDataForm.controls['name'].value,
        surname: this.clientDataForm.controls['surname'].value,
        city: this.clientDataForm.controls['city'].value,
        street: this.clientDataForm.controls['street'].value,
        zip: this.clientDataForm.controls['zip'].value,
        cellphone: this.clientDataForm.controls['cellphone'].value,
        houseNumber: this.clientDataForm.controls['houseNumber'].value,
        telephone: this.clientDataForm.controls['telephone'].value,
        email: this.clientDataForm.controls['email'].value,
      },
      personalComputer: {
        computerCase: this.personalComputerForm.controls['computerCase'].value,
        motherboard: this.personalComputerForm.controls['motherboard'].value,
        processor: this.personalComputerForm.controls['processor'].value,
        graphicsCard: this.personalComputerForm.controls['graphicsCard'].value,
        powerSupplyUnit: this.personalComputerForm.controls['powerSupplyUnit'].value,
        randomAccessMemory: this.personalComputerForm.controls['randomAccessMemory'].value,
        storageDevice: this.personalComputerForm.controls['storageDevice'].value,
      },
      privacyPolicy: true,
      files: this.documentsDataSource.data.map(value => {
        return {
          id: value.id || '',
          name: value.name || '',
          note: value.note || ''
        }
      })
    }

    this.dialogRef.close({
      documents: this.documentsDataSource.data,
      newInternalOrder: newInternalOrder
    });

    const formDate = new FormData();
    formDate.set('internal-order', JSON.stringify(newInternalOrder));

    this.documentsDataSource.data.forEach(value => {
      if (value.file != null && value.id != null) {
        formDate.append(value.id, value.file);
      }
    })

    this.store.dispatch(addInternalOrder({
      newInternalOrderMPDTO: newInternalOrder,
      formData: formDate
    }));
  }

  resetClick(): void {
    this.stepper.reset();
    this.documentsDataSource.data = [];
  }

  addDocumentClick(): void {
    this.dialog.open<AddDocumentToNewInternalOrderComponent, any, AddDocumentOutput>(AddDocumentToNewInternalOrderComponent, {
      minWidth: '40em',
      data: {},
      disableClose: true
    }).afterClosed().subscribe(result => {
      if (result && result.button == Buttons.UPLOAD) {

        this.documentsDataSource.data = [...this.documentsDataSource.data, {
          id: generateId(),
          name: result.name,
          note: result.note,
          file: result.file
        }];
      }
    });
  }
}

export interface Document {
  id?: string;
  name?: string;
  note?: string;
  file?: File;
}

export interface NewInternalOrderOutput {
  documents: Document[];
  newInternalOrder: NewInternalOrderMPDTO;
}
