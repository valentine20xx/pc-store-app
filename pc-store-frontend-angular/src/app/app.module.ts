import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FlexLayoutModule} from '@angular/flex-layout';
import {ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatStepperModule} from '@angular/material/stepper';
import {MatTableModule} from '@angular/material/table';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatTooltipModule} from '@angular/material/tooltip';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterModule} from '@angular/router';
import {EffectsModule} from '@ngrx/effects';
import {StoreModule} from '@ngrx/store';
import {StoreDevtoolsModule} from '@ngrx/store-devtools';
import {environment} from '../environments/environment';
import {Stages} from '../environments/model';
import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
import {ErrorDialogComponent} from './error-dialog/error-dialog.component';
import {InternalOrderOverviewComponent} from './internal-orders-overview/internal-order-overview/internal-order-overview.component';
import {InternalOrdersOverviewComponent} from './internal-orders-overview/internal-orders-overview.component';
import {AddDocumentToNewInternalOrderComponent} from './internal-orders-overview/new-internal-order/add-document-to-new-internal-order/add-document-to-new-internal-order.component';
import {NewInternalOrderComponent} from './internal-orders-overview/new-internal-order/new-internal-order.component';
import {LongTextShortComponent} from './long-text-short/long-text-short.component';
import {InternalOrdersService, InternalOrdersServiceLocal} from './services/internal-orders.service';
import {InternalOrderEffects} from './state/app.effects';
import {InternalOrdersState, internalOrdersStateReducer, InternalOrderState, internalOrderStateReducer, LoginState, loginStateReducer} from './state/app.reducer';
import {AutoTrimDirective} from './utils/directives/auto-trim.directive';
import {ReferenceConverterPipe} from './utils/pipes/reference-converter.pipe';
import {WellcomePageComponent} from './wellcome-page/wellcome-page.component';

@NgModule({
  declarations: [
    AppComponent,
    InternalOrdersOverviewComponent,
    WellcomePageComponent,
    NewInternalOrderComponent,
    AutoTrimDirective,
    ReferenceConverterPipe,
    AddDocumentToNewInternalOrderComponent,
    ErrorDialogComponent,
    InternalOrderOverviewComponent,
    LongTextShortComponent
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
    StoreModule.forRoot<AppState>({
      internalOrdersState: internalOrdersStateReducer,
      internalOrderState: internalOrderStateReducer,
      loginState: loginStateReducer
    }),
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
  internalOrderState: InternalOrderState,
  loginState: LoginState,
}
