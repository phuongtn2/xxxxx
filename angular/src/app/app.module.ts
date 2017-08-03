import { BrowserModule, Title } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LOCALE_ID, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';
import { CalendarModule } from 'primeng/primeng';
import { AppCalendarModule } from './app-calendar/app-calendar.module';

import { AuthGuard } from './core/guards/auth.guard';
import { HttpService } from './core/services/http.service';
import { AuthenticationService } from './core/services/authentication.service';
import { DialogService } from './core/services/dialog.service';
import { NotificationService } from './core/services/notification.service';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ConfirmDialogComponent } from './core/components/confirm-dialog/confirm-dialog.component';
import { SettingsComponent } from './settings/settings.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { CompanyMaterialsModule } from './company-materials/company-materials.module';
import { CropSessionModule } from './crop-session/crop-session.module';
import { DocumentsModule } from './documents/documents.module';
import { FarmerCostModule } from './farmer-cost/farmer-cost.module';
import { FarmersModule } from './farmers/farmers.module';
import { MaterialRequestsModule } from './material-requests/material-requests.module';
import { ReportsModule } from './reports/reports.module';
import { SharedModule } from './shared';
import { TasksModule } from './tasks/tasks.module';
import { UsersModule } from './users/users.module';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ConfirmDialogComponent,
    SettingsComponent,
    ForgotPasswordComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    CompanyMaterialsModule,
    CropSessionModule,
    DocumentsModule,
    FarmerCostModule,
    FarmersModule,
    MaterialRequestsModule,
    ReportsModule,
    SharedModule,
    TasksModule,
    UsersModule,
    AppRoutingModule,
    FlexLayoutModule,
    MaterialModule,
    MdDataTableModule,
    ReactiveFormsModule,
    CalendarModule,
    AppCalendarModule,
  ],
  providers: [
    Title, AuthGuard, HttpService, AuthenticationService,
    DialogService, NotificationService,
    { provide: LOCALE_ID, useValue: 'vi-VN' }
  ],
  entryComponents: [
    ConfirmDialogComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
