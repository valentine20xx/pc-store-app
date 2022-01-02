import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from "@angular/material/button";
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatRadioModule} from '@angular/material/radio';
import {AppRoutingModule} from './app-routing.module';
import {InternalOrdersOverviewComponent} from './internal-orders-overview/internal-orders-overview.component';
import {MatListModule} from '@angular/material/list';
import {MatTooltipModule} from '@angular/material/tooltip';
import {RouterModule} from '@angular/router';
import {MatMenuModule} from '@angular/material/menu';
import {GlobalVariablesOverviewComponent} from './global-variables-overview/global-variables-overview.component';
import {WellcomePageComponent} from './wellcome-page/wellcome-page.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MatTableModule} from "@angular/material/table";
import {NewInternalOrderComponent} from './internal-orders-overview/new-internal-order/new-internal-order.component';
import {MatDialogModule} from '@angular/material/dialog';
import {MatStepperModule} from '@angular/material/stepper';
import {ReactiveFormsModule} from '@angular/forms';
import {AutoTrimDirective} from './utils/directives/auto-trim.directive';
import {MatInputModule} from '@angular/material/input';
import {ReferenceConverterPipe} from './utils/pipes/reference-converter.pipe';
import {AddDocumentToNewInternalOrderComponent} from './internal-orders-overview/new-internal-order/add-document-to-new-internal-order/add-document-to-new-internal-order.component';
import {ErrorDialogComponent} from './error-dialog/error-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    InternalOrdersOverviewComponent,
    GlobalVariablesOverviewComponent,
    WellcomePageComponent,
    NewInternalOrderComponent,
    AutoTrimDirective,
    ReferenceConverterPipe,
    AddDocumentToNewInternalOrderComponent,
    ErrorDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatSidenavModule,
    MatFormFieldModule,
    MatSelectModule,
    MatRadioModule,
    AppRoutingModule,
    MatListModule,
    MatTooltipModule,
    RouterModule,
    MatMenuModule,
    FlexLayoutModule,
    MatTableModule,
    MatDialogModule,
    MatStepperModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}