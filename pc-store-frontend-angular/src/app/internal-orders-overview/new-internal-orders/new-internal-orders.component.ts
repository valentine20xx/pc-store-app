import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatStepper} from "@angular/material/stepper";

@Component({
  selector: 'app-new-internal-orders',
  templateUrl: './new-internal-orders.component.html',
  styleUrls: ['./new-internal-orders.component.css']
})
export class NewInternalOrdersComponent implements OnInit, AfterViewInit {
  @ViewChild('stepper') stepper!: MatStepper;

  personalComputerForm = this.formBuilder.group({
    computerCase: [''],
    motherboard: [''],
    processor: [''],
    graphicsCard: [''
      // , [Validators.required]
    ],
    randomAccessMemory: [''],
    storageDevice: [''],
    powerSupplyUnit: ['']
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

  documentsDisplayedColumns: string[] = ['name', 'notes', 'actions'];
  documents = [{name: 'file1', notes: 'Quittung 1'},
    {name: 'file2', notes: 'Quittung 2'}]

  salutations: IdNamePair[] = [
    {id: 'salutations-herr', name: 'Herr'},
    {id: 'salutations-frau', name: 'Frau'}
  ];

  constructor(private formBuilder: FormBuilder,
              private dialogRef: MatDialogRef<any, any>,
              @Inject(MAT_DIALOG_DATA) public input: any) {
  }

  ngOnInit(): void {
  }

  stepperNextClick(): void {
    this.stepper.next();
  }

  stepperPreviousClick(): void {
    this.stepper.previous()
  }

  newInternalOrderCreate(): void {
    this.dialogRef.close();
  }

  ngAfterViewInit(): void {
    console.log(this.stepper);
  }
}

export interface IdNamePair {
  id: string;
  name: string;
}
