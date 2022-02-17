import {AfterViewInit, Component, Inject, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatSidenav} from '@angular/material/sidenav';
import {ActivatedRoute, Router} from '@angular/router';
import {select, Store} from '@ngrx/store';
import {filter, skip, Subscription} from 'rxjs';
import {environment} from 'src/environments/environment';
import {AppState} from './app.module';
import {login, logout} from './state/app.actions';
import {LoginState} from './state/app.reducer';
import {loginStateFeature} from './state/app.selectors';
import {ErrorOverlayService} from './utils/error-overlay.service';
import {LoaderService} from './utils/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('mainSidenav') mainSidenav!: MatSidenav;

  environment = environment;
  one?: Subscription;
  loginState?: LoginState

  constructor(private router: Router,
              private route: ActivatedRoute,
              private store: Store<AppState>,
              private loaderService: LoaderService,
              private errorOverlayService: ErrorOverlayService,
              @Inject('IS_PRODUCTION') public isProduction: boolean) {
  }

  internalOrdersOverviewButtonClick(): void {
    this.router.navigate(['/internal-orders-overview'], {relativeTo: this.route}).then(() => {
      this.mainSidenav.close().then(value => value);
    });
  }

  loginClick(): void {
    this.store.dispatch(login());
  }

  logoutClick(): void {
    this.store.dispatch(logout());
  }

  ngAfterViewInit(): void {
  }

  ngOnDestroy(): void {
  }

  ngOnInit(): void {
    this.one = this.store.pipe(select(loginStateFeature), filter(value => value.status != 'INITIALIZED' && value.status != 'CLOSED')).subscribe(value => {
        this.loginState = value;

        switch (value.status) {
          case 'SEND':
            this.loaderService.showSpinner()
            break;
          case 'FAILURE':
            this.loaderService.hideSpinner();
            this.errorOverlayService.showErrorOverlay('Unable to login', () => {
              this.store.dispatch(login());
            })
            break;
          case 'RECEIVED':
            this.loaderService.hideSpinner();
            break;
        }
      }
    );
  }
}
