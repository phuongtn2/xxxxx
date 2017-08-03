import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VideoPopupComponent } from './video-popup/video-popup.component';
import { MediaGalleryComponent } from './media-gallery/media-gallery.component';
import { ImagePopupComponent } from './image-popup/image-popup.component';

@NgModule({
  declarations: [
    ImagePopupComponent,
    MediaGalleryComponent,
    VideoPopupComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ImagePopupComponent,
    MediaGalleryComponent,
    VideoPopupComponent
  ],
  entryComponents: [
    ImagePopupComponent,
    VideoPopupComponent
  ]
})
export class SharedModule { }
