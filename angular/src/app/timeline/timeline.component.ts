import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

import { AuthenticationService } from '../core/services/authentication.service';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.scss']
})
export class TimelineComponent implements OnInit {
  @Input() orderedTimeline;

  constructor(
    private sanitizer: DomSanitizer,
    private authService: AuthenticationService
  ) { }

  ngOnInit() {
  }

  getSafeMediaUrl(fileId) {
    fileId = encodeURIComponent(fileId);
    return this.sanitizer.bypassSecurityTrustStyle(
      `url(/api/file/download/${fileId}?access_token=${this.authService.getToken()})`
    );
  }
}
