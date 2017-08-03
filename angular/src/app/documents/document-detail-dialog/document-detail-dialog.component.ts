import { Component, OnInit, ViewChild } from '@angular/core';
import { MdDialogRef } from '@angular/material';
import { Observable } from 'rxjs/Observable';

import { ServerError } from '../../core/objects/server-error';
import { NotificationService } from '../../core/services/notification.service';
import { Document } from '../document';
import { DocumentService } from '../document.service';

@Component({
  selector: 'app-document-detail-dialog',
  templateUrl: './document-detail-dialog.component.html',
  styleUrls: ['./document-detail-dialog.component.scss']
})
export class DocumentDetailDialogComponent implements OnInit {
  title: string;
  doc: Document;
  @ViewChild('fileInput') fileInput;

  constructor(
    private dialogRef: MdDialogRef<DocumentDetailDialogComponent>,
    private documentService: DocumentService,
    private notification: NotificationService
  ) { }

  ngOnInit() {
    this.title = 'Sửa tài liệu';
    if (!this.doc) {
      this.doc = new Document();
      this.title = 'Thêm tài liệu';
    }
  }

  saveDocument() {
    if (this.fileInput) {
      const fi = this.fileInput.nativeElement;
      if (fi.files &&  fi.files[0]) {
        this.doc.file = fi.files[0];
      }
    }
    this.documentService.saveDocument(this.doc)
      .catch(
        response => {
          if (response.status !== 200) {
            this.notification.notify('Chỉ cho phép upload các loại tài liệu: txt, doc, docx, xls, xlsx, ppt, pptx, pdf');
          }
          return Observable.throw(response);
        }
      )
      .subscribe(
        newDocument => {
          this.notification.notify('Lưu tài liệu.');
          this.dialogRef.close(newDocument);
        }
      );
  }

  inputFileChange(evt) {
    this.doc.documentName = evt.target.value.split('\\').pop();
  }
}
