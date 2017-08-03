import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'fromCharCode'
})
export class FromCharCodePipe implements PipeTransform {

  transform(value: any, args?: any): any {
    return String.fromCharCode(Number.parseInt(value));
  }

}
