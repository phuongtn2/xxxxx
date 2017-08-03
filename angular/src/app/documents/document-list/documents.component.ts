import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { MdDialog, MdDialogConfig } from '@angular/material';
import { Title } from '@angular/platform-browser';

import { DialogService } from '../../core/services/dialog.service';
import { NotificationService } from '../../core/services/notification.service';
import { Document } from '../document';
import { DocumentService } from '../document.service';
import { DocumentDetailDialogComponent } from '../document-detail-dialog/document-detail-dialog.component';

@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.scss']
})
export class DocumentsComponent implements OnInit {
  title;
  documents: Document[];
  limit = 10;

  constructor(
    private titleService: Title,
    private dialog: MdDialog,
    private viewContainerRef: ViewContainerRef,
    private documentsService: DocumentService,
    private dialogService: DialogService,
    private notification: NotificationService
  ) {
    this.title = 'Danh sách tài liệu';
  }

  ngOnInit() {
    this.setTitle();
    this.getDocuments();
  }

  deleteDocument(document: Document) {
    this.dialogService.confirm(null, 'Xóa tài liệu này?', this.viewContainerRef)
      .subscribe(result => {
        if (result === true) {
          this.documentsService.deleteDocument(document.id)
            .subscribe(
              (ok) => {
                if (ok) {
                  this.notification.notify(`${document.documentName} đã được xóa.`);
                  // refresh page
                  this.getDocuments();
                }
              }
            );
        }
      });
  }

  downloadDocument(document: Document) {
    this.documentsService.downloadDocument(document.id.toString());
  }

  openDialog(document?: Document) {
    const config = new MdDialogConfig();
    config.width = '320px';
    const dialogRef = this.dialog.open(DocumentDetailDialogComponent, config);
    if (document) {
      // send only a clone of document object to the detail dialog
      dialogRef.componentInstance.doc = Document.assign(document);
    }
    dialogRef.afterClosed().subscribe(
      (result) => {
        if (result) {
          // refresh page
          this.getDocuments();
        }
      }
    );
  }

  private getDocuments() {
    this.documentsService.getAll()
      .subscribe(
        (documents) => {
          this.documents = documents;
        }
      );
  }

  private setTitle() {
    this.titleService.setTitle(this.title);
  }
}
