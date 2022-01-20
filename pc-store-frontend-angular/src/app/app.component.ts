import {Component, Inject, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatSidenav} from '@angular/material/sidenav';
import {environment} from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  @ViewChild('mainSidenav') mainSidenav!: MatSidenav;

  environment = environment;

  constructor(private router: Router,
              private route: ActivatedRoute,
              @Inject('IS_PRODUCTION') public isProduction: boolean) {
  }

  internalOrdersOverviewButtonClick(): void {
    this.router.navigate(['/internal-orders-overview'], {relativeTo: this.route}).then(() => {
      this.mainSidenav.close().then(value => value);
    });
  }
}
