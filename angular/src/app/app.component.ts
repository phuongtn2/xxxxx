import { Component, OnInit } from '@angular/core';
import { NavigationEnd, NavigationError, NavigationStart, Router } from '@angular/router';

import { AuthenticationService } from './core/services/authentication.service';
import { NotificationService } from './core/services/notification.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  loading = false;

  constructor(
    private router: Router,
    private notification: NotificationService,
    public authService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.router.events.subscribe(
      (event) => {
        if (event instanceof NavigationStart) {
          this.loading = true;
        }
        if (event instanceof NavigationEnd) {
          this.loading = false;
        }
        if (event instanceof NavigationError) {
          this.notification.notify('Lỗi khi chuyển trang.');
        }
      }
    );
  }

  isLoggedIn() {
    return this.authService.currentUser != null;
  }
}
