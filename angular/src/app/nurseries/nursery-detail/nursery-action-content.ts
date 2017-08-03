import { Injectable } from '@angular/core';

import { PestService } from '../../core/services/pest.service';

export class NurseryActionContent {
  clientPrimaryKey: number;
  id: number;
  nurseryId: number;
  actionId: number;
  pestId: number;
  pestName: string;
  pesticideId: number;
  materialName: string;
  materialUnit: string;
  ratio: number;
}

@Injectable()
export class NurseryPestAction extends NurseryActionContent {
  pestId: number;

  get pest() {
    return this.pestService.get(this.pestId);
  }

  constructor(private pestService: PestService) {
    super();
  }
}
