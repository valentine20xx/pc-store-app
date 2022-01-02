import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, Validators} from "@angular/forms";

export enum Buttons {
  UPLOAD, CLOSE
}

export interface AddDocumentOutput {
  button?: Buttons;
  name?: string;
  note?: string;
  file?: File;
}

@Component({
  selector: 'app-add-document-to-new-internal-order',
  templateUrl: './add-document-to-new-internal-order.component.html',
  styleUrls: ['./add-document-to-new-internal-order.component.css']
})
export class AddDocumentToNewInternalOrderComponent {
  fileUploadForm = this.formBuilder.group({
    name: ['', Validators.required],
    note: [''],
    file: ['', Validators.required]
  });

  constructor(private formBuilder: FormBuilder,
              private dialogRef: MatDialogRef<any, AddDocumentOutput>,
              @Inject(MAT_DIALOG_DATA) public input: any) {
  }

  newDocument: AddDocumentOutput = {};

  onChangeFileInput(event: any): void {
    const files: File[] = event.target.files;
    if (files.length === 1) {
      const file = files[0];
      this.newDocument.file = file;

      if (this.fileUploadForm.value.name === '') {
        this.fileUploadForm.controls['name'].setValue(file.name);
      }
    }
  }

  onChangeFileName(event: any): void {
    this.newDocument.name = event;
  }

  onCancelClick(): void {
    this.dialogRef.close({button: Buttons.CLOSE});
  }

  onSubmitClick(): void {
    if(this.fileUploadForm.valid){

    }else {

    }

    // this.newDocument.button = Buttons.UPLOAD;
    // this.dialogRef.close(this.newDocument);
  }
}


