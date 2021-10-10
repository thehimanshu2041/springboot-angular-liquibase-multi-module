import { Subscription } from 'rxjs';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tsWorking',
  pure: false
})
export class WorkingPipe implements PipeTransform {

  transform(sub: Subscription): boolean {
    return sub && !sub.closed;
  }
}
