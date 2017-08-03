import { TestBed, inject } from '@angular/core/testing';
import { MdDialogModule } from '@angular/material';

import { DialogService } from './dialog.service';

describe('DialogService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        MdDialogModule
      ],
      providers: [
        DialogService
      ]
    });
  });

  it('should ...', inject([DialogService], (service: DialogService) => {
    expect(service).toBeTruthy();
  }));
});
