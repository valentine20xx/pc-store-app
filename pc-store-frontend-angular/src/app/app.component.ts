import {AfterViewInit, Component, Inject, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatSidenav} from '@angular/material/sidenav';
import {environment} from 'src/environments/environment';
import {select, Store} from "@ngrx/store";
import {AppState} from "./app.module";
import {skip, Subscription} from "rxjs";
import {loginStateFeature} from "./state/app.selectors";
import {login, logout} from "./state/app.actions";
import {LoginState} from "./state/app.reducer";
import {LoaderService} from "./utils/loader.service";
import {ErrorOverlayService} from "./utils/error-overlay.service";

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
    this.one = this.store.pipe(select(loginStateFeature), skip(1)).subscribe(value => {
        console.log('loginState', value);
        this.loginState = value;

        value.loading ? this.loaderService.showSpinner() : this.loaderService.hideSpinner();
        return value.hasError ? this.errorOverlayService.showErrorOverlay('Unable to login', () => {
          this.store.dispatch(login());
        }) : null;
      }
    );
  }
}
