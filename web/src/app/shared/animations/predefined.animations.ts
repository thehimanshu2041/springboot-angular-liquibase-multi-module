import {
	AnimationTriggerMetadata,
	animate,
	query,
	stagger,
	state,
	style,
	transition,
	trigger
} from '@angular/animations';

export const predefinedAnimations: { [name: string]: AnimationTriggerMetadata } = {
	flyInOut: trigger('flyInOut', [
		state('out', style({})),
		state('in', style({})),
		transition('void => in', [style({ transform: 'translateY(20%)', opacity: '0' }), animate(200)]),
		transition('in => void', [animate(200, style({ transform: 'translateX(-50%)', opacity: '0' }))]),
		transition('void => out', [style({ transform: 'translateY(-20%)', opacity: '0' }), animate(200)]),
		transition('out => void', [animate(200, style({ transform: 'translateX(50%)', opacity: '0' }))])
	]),
	slide: trigger('slide', [
		state('down', style({})),
		state('up', style({})),
		state('in', style({})),
		transition('void => up', [style({ transform: 'translateY(-50%)', opacity: '0' }), animate(250)]),
		transition('up => void', [animate(250, style({ transform: 'translateY(-50%)', opacity: '0' }))]),
		transition('void => down', [style({ transform: 'translateY(50%)', opacity: '0' }), animate(250)]),
		transition('down => void', [animate(250, style({ transform: 'translateY(50%)', opacity: '0' }))]),
		transition('void => in', [style({ height: 0, overflow: 'hidden' }), animate('.25s')]),
		transition('in => void', [animate('.25s', style({ height: 0, overflow: 'hidden' }))])
	]),
	list: trigger('list', [
		transition('* => *', [
			query('div.mat-list-item-content', style({ transform: 'translateX(-100%)' }), { optional: true }),
			query('div.mat-list-item-content', stagger('100ms', [animate('300ms', style({ transform: 'translateX(0)' }))]), {
				optional: true
			})
		])
	]),
	listRight: trigger('listRight', [
		transition('* => *', [
			query('div.mat-list-item-content', style({ transform: 'translateX(100%)' }), { optional: true }),
			query('div.mat-list-item-content', stagger('100ms', [animate('300ms', style({ transform: 'translateX(0)' }))]), {
				optional: true
			})
		])
	]),
	slideInOut: trigger('slideInOut', [
		transition(':enter', [style({ height: 0, overflow: 'hidden' }), animate('.25s')]),
		transition(':leave', [animate('.25s', style({ height: 0, overflow: 'hidden' }))])
	]),
	fader:
		trigger('routeAnimations', [
			transition('* <=> *', [
				// Set a default  style for enter and leave
				query(':enter, :leave', [
					style({
						position: 'absolute',
						left: 0,
						width: '100%',
						opacity: 0,
						transform: 'scale(0) translateY(100%)',
					}),
				]),
				// Animate the new page in
				query(':enter', [
					animate('600ms ease', style({ opacity: 1, transform: 'scale(1) translateY(0)' })),
				])
			]),
		]),
	fade:
		trigger('fade', [
			transition('void => *', [
				style({ opacity: 0 }),
				animate(1000, style({ opacity: 1 }))
			])
		]),

	filterListAnimation:
		trigger('filterListAnimation', [
			transition(':enter, * => 0, * => -1', []),
			transition(':increment', [
				query(':enter', [
					style({ opacity: 0, width: '0px' }),
					stagger(50, [
						animate('300ms ease-out', style({ opacity: 1, width: '*' })),
					]),
				], { optional: true })
			]),
			transition(':decrement', [
				query(':leave', [
					stagger(50, [
						animate('300ms ease-out', style({ opacity: 0, width: '0px' })),
					]),
				])
			]),
		]),
	fadeAnimation:
		trigger('fadeAnimation', [

			transition('* => *', [

				query(':enter',
					[
						style({ opacity: 0 })
					],
					{ optional: true }
				),

				query(':leave',
					[
						style({ opacity: 1 }),
						animate('0.5s', style({ opacity: 0 }))
					],
					{ optional: true }
				),

				query(':enter',
					[
						style({ opacity: 0 }),
						animate('0.5s', style({ opacity: 1 }))
					],
					{ optional: true }
				)

			])

		]),
	zoomInOutAnimations:
		trigger('zoomInOut', [
			transition(':enter', [style({ transform: 'scale(0.7)' }), animate('.25s')]),
			transition(':leave', [animate('.25s', style({ transform: 'scale(0.7)' }))])
		])

};
