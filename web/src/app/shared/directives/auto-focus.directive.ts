import { AfterViewInit, Directive, ElementRef, Input } from '@angular/core';

@Directive({ selector: '[tsAutoFocus]' })
export class AutoFocusDirective implements AfterViewInit {
	@Input()
	tsAutoFocus: boolean = true;

	constructor(private _el: ElementRef<HTMLElement>) {}

	ngAfterViewInit() {
		if (this.tsAutoFocus) {
			this._el.nativeElement.focus();
		}
	}
}
