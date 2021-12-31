import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {InternalOrdersOverviewComponent} from "./internal-orders-overview/internal-orders-overview.component";
import {GlobalVariablesOverviewComponent} from "./global-variables-overview/global-variables-overview.component";
import {WellcomePageComponent} from "./wellcome-page/wellcome-page.component";

const routes: Routes = [
  {path: '', component: WellcomePageComponent},
  {path: 'internal-orders-overview', component: InternalOrdersOverviewComponent},
  {path: 'global-variables-overview', component: GlobalVariablesOverviewComponent}
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes),
    CommonModule
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
