import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '@angular/material';
import { MdDataTableModule } from '@dtrthi/md-data-table';

import { DocumentDetailDialogComponent } from './document-detail-dialog/document-detail-dialog.component';
import { DocumentsComponent } from './document-list/documents.component';
import { DocumentsRoutingModule } from './documents-routing.module';
import { DocumentService } from './document.service';

@NgModule({
  declarations: [
    DocumentDetailDialogComponent,
    DocumentsComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    FormsModule,
    MaterialModule,
    MdDataTableModule,
    ReactiveFormsModule,
    DocumentsRoutingModule
  ],
  providers: [
    DocumentService
  ],
  entryComponents: [
    DocumentDetailDialogComponent
  ]
})
export class DocumentsModule { }
