import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatRadioModule} from '@angular/material/radio';
import {MatListModule} from '@angular/material/list';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatMenuModule} from '@angular/material/menu';
import {MatTableModule} from '@angular/material/table';
import {MatDialogModule} from '@angular/material/dialog';
import {MatStepperModule} from '@angular/material/stepper';
import {MatInputModule} from '@angular/material/input';
import {MatPaginatorModule} from '@angular/material/paginator';
import {ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {FlexLayoutModule} from '@angular/flex-layout';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {HttpClientModule} from '@angular/common/http';
import {StoreModule} from '@ngrx/store';
import {EffectsModule} from '@ngrx/effects';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {InternalOrdersOverviewComponent} from './internal-orders-overview/internal-orders-overview.component';
import {WellcomePageComponent} from './wellcome-page/wellcome-page.component';
import {NewInternalOrderComponent} from './internal-orders-overview/new-internal-order/new-internal-order.component';
import {AutoTrimDirective} from './utils/directives/auto-trim.directive';
import {ReferenceConverterPipe} from './utils/pipes/reference-converter.pipe';
import {AddDocumentToNewInternalOrderComponent} from './internal-orders-overview/new-internal-order/add-document-to-new-internal-order/add-document-to-new-internal-order.component';
import {ErrorDialogComponent} from './error-dialog/error-dialog.component';
import {InternalOrderEffects} from './state/app.effects';
import {internalOrdersStateReducer, InternalOrdersState, loginStateReducer, LoginState} from "./state/app.reducer";
import {environment} from '../environments/environment';
import {Stages} from '../environments/model';
import {InternalOrdersService, InternalOrdersServiceLocal} from './services/internal-orders.service';

@NgModule({
  declarations: [
    AppComponent,
    InternalOrdersOverviewComponent,
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
    HttpClientModule,
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
    MatInputModule,
    MatPaginatorModule,
    StoreModule.forRoot<AppState>({internalOrdersState: internalOrdersStateReducer, loginState: loginStateReducer}),
    EffectsModule.forRoot([InternalOrderEffects]),
    StoreDevtoolsModule.instrument({
      maxAge: 25, // Retains last 25 states
      logOnly: environment.stage == Stages.PROD, // Restrict extension to log-only mode
      autoPause: true, // Pauses recording actions and state changes when the extension window is not open
    })
  ],
  providers: [{
    provide: InternalOrdersService,
    useClass: (environment.stage === Stages.LOCAL ? InternalOrdersServiceLocal : InternalOrdersService)
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}

export interface AppState {
  internalOrdersState: InternalOrdersState,
  loginState: LoginState
}
