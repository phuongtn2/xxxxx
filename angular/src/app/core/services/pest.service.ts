import { Injectable } from '@angular/core';

import { CommonService } from './common.service';
import { Pest } from '../models/pest';

@Injectable()
export class PestService {

  constructor(
    private commonService: CommonService
  ) { }

  get(id: number) {
    return this.commonService.getPest()
      .map(
        (pests: Pest[]) => {
          return pests.find(pest => pest.id === id);
        }
      );
  }
}
