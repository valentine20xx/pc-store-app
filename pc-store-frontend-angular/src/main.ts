import {enableProdMode} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

import {AppModule} from './app/app.module';
import {environment} from './environments/environment';
import {Stages} from './environments/model';

if (environment.stage == Stages.PROD) {
  enableProdMode();
}

platformBrowserDynamic([{
  provide: 'IS_PRODUCTION',
  useValue: environment.stage === Stages.PROD
}]).bootstrapModule(AppModule).catch(err => console.error(err));
