import { Directive, ElementRef, HostBinding, HostListener, Input, OnDestroy, OnInit } from '@angular/core';
import { ConnectedPosition, Overlay, OverlayPositionBuilder, OverlayRef } from '@angular/cdk/overlay';
import { ComponentPortal } from '@angular/cdk/portal';
import { TooltipComponent } from '../components/tooltip';

type TooltipPosition = 'top' | 'left' | 'bottom' | 'right';

@Directive({ selector: '[tsTooltip]' })
export class TooltipDirective implements OnInit, OnDestroy {
  @Input('tsTooltip') content = '';
  private overlayRef: OverlayRef;

  @Input()
  private tsTooltipSize: 'sm' | 'md' | 'lg' | null = null;

  @Input()
  private tsTooltipPos: TooltipPosition = 'top';

  @HostBinding('style.cursor')
  private _cursor: string = 'pointer';

  constructor(
    private overlay: Overlay,
    private overlayPositionBuilder: OverlayPositionBuilder,
    private elementRef: ElementRef
  ) { }

  ngOnInit() {
    const positionStrategy = this.overlayPositionBuilder
      .flexibleConnectedTo(this.elementRef)
      .withPositions(this.getPositionStrategy(this.tsTooltipPos));

    this.overlayRef = this.overlay.create({ positionStrategy });
  }

  ngOnDestroy() {
    this.overlayRef.dispose();
  }

  getPositionStrategy(position: TooltipPosition): ConnectedPosition[] {
    switch (position) {
      case 'top':
        return [
          { originX: 'center', originY: 'top', overlayX: 'center', overlayY: 'bottom' },
          { originX: 'center', originY: 'bottom', overlayX: 'center', overlayY: 'top' }
        ];
      case 'left':
        return [
          { originX: 'start', originY: 'center', overlayX: 'end', overlayY: 'center' },
          { originX: 'end', originY: 'center', overlayX: 'start', overlayY: 'center' }
        ];
      case 'bottom':
        return [
          { originX: 'center', originY: 'bottom', overlayX: 'center', overlayY: 'top' },
          { originX: 'center', originY: 'top', overlayX: 'center', overlayY: 'bottom' }
        ];
      case 'right':
        return [
          { originX: 'end', originY: 'center', overlayX: 'start', overlayY: 'center' },
          { originX: 'start', originY: 'center', overlayX: 'end', overlayY: 'center' }
        ];
    }
  }

  @HostListener('mouseenter')
  @HostListener('touchstart')
  show() {
    if (this.content && !this.overlayRef.hasAttached()) {
      let compRef = this.overlayRef.attach(new ComponentPortal(TooltipComponent));
      if (!this.tsTooltipSize) {
        const v = this.content.replace(/<[^>]*>/g, '');
        if (v.length < 201) {
          this.tsTooltipSize = 'sm';
        } else if (v.length < 401) {
          this.tsTooltipSize = 'md';
        } else {
          this.tsTooltipSize = 'lg';
        }
      }

      compRef.instance.tooltipSize = `adv-tooltip-${this.tsTooltipSize}`;
      compRef.instance.content = this.content;
    }
  }

  @HostListener('window:scroll')
  @HostListener('mouseout')
  hide() {
    this.overlayRef.detach();
  }
}
