import { AbstractControl, ValidationErrors } from '@angular/forms';

export function requiredClass<T>(clazz: new () => T) {
  return (control: AbstractControl): ValidationErrors | null => {
    return control.value instanceof clazz ? null : {requiredClass: true};
  };
}
