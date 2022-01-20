import {Component, Inject, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder} from "@angular/forms";
import {MatStepper} from "@angular/material/stepper";
import {AddDocumentOutput, AddDocumentToNewInternalOrderComponent, Buttons} from "./add-document-to-new-internal-order/add-document-to-new-internal-order.component";
import {generateId} from "../../utils/utils";
import {MatTableDataSource} from "@angular/material/table";
import {getSalutations, InternalOrderShortDTO} from "../../model/model";

@Component({
  selector: 'app-new-internal-order',
  templateUrl: './new-internal-order.component.html',
  styleUrls: ['./new-internal-order.component.css']
})
export class NewInternalOrderComponent {
  @ViewChild('stepper') stepper!: MatStepper;

  personalComputerForm = this.formBuilder.group({
    computerCase: [''
      // , [Validators.required]
    ],
    motherboard: [''
      // , [Validators.required]
    ],
    processor: [''
      // , [Validators.required]
    ],
    graphicsCard: [''
      // , [Validators.required]
    ],
    randomAccessMemory: [''
      // , [Validators.required]
    ],
    storageDevice: [''
      // , [Validators.required]
    ],
    powerSupplyUnit: [''
      // , [Validators.required]
    ]
  });

  clientDataForm = this.formBuilder.group({
    salutation: [''
      // , [Validators.required]
    ],
    name: [''
      //, [Validators.required, Validators.pattern('^[A-Za-zÖÄÜöäüß\ \-]*$')]
    ],
    surname: [''
      //, [Validators.required, Validators.pattern('^[A-Za-zÖÄÜöäüß\ \-]*$')]
    ],
    street: [''
      // , [Validators.required, Validators.pattern('^[A-Za-zÖÄÜöäüß\ \-.&]*$')]
    ],
    houseNumber: [''
      // , [Validators.required, Validators.pattern('^([0-9A-Za-zÖÄÜöäüß])*$')]
    ],
    zip: [''
      // , [Validators.required, Validators.pattern('^(\\d{5})$')]
    ],
    city: [''
      // , [Validators.required, Validators.pattern('^[A-Za-zÖÄÜöäüß\ \-.&]*$')]
    ],
    telephone: [''
      // , Validators.pattern('^\\+{0,1}([0-9\ ])*$')
    ],
    cellphone: [''
      // , Validators.pattern('^\\+{0,1}([0-9\ ])*$')
    ],
    email: [''
      // , Validators.pattern('^.{1,}@.{1,}\\..{2,}$')
    ]
  });

  documentsDisplayedColumns: string[] = ['name', 'note', 'actions'];
  documentsDataSource = new MatTableDataSource<Document>();

  salutations = getSalutations();

  constructor(private formBuilder: FormBuilder,
              private dialog: MatDialog,
              private dialogRef: MatDialogRef<any, InternalOrderShortDTO>,
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
    this.dialogRef.close({
      id: generateId(),
      version: new Date(),
      client: `${this.clientDataForm.controls['surname'].value}, ${this.clientDataForm.controls['name'].value}`,
      personalComputer: `${this.personalComputerForm.controls['processor'].value}, ${this.personalComputerForm.controls['graphicsCard'].value}`,
      status: 'open',
      dateOfReceiving: new Date()
    });
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
