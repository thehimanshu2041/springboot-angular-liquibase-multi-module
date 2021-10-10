import {
	AnimationTriggerMetadata,
	animate,
	query,
	stagger,
	state,
	style,
	transition,
	trigger,
    animateChild,
    group
} from '@angular/animations';

export const ROUTE_CHANGING_ANIMATION = trigger('routeChanging', [
	state('end', style({ opacity: 1 })),
	state('start', style({ opacity: 0 })),
	transition('start => end', [animate('.5s')])
]);

export const ROUTE_CHANGING_ANIMATION_SLIDE = trigger('routeChanging', [
	transition('end => start', [
		style({ position: 'relative', overflow: 'hidden' }),
		query(
			':enter, :leave',
			[
				style({
					position: 'absolute',
					left: 0,
					width: '100%'
				})
			],
			{ optional: true }
		),
		query(':enter', [style({ left: '-100%', opacity: 0.5 })], { optional: true }),
		query(':leave', animateChild(), { optional: true }),
		group([
			query(':leave', [animate('300ms ease-out', style({ left: '100%' }))], { optional: true }),
			query(':enter', [animate('300ms ease-out', style({ left: '0%', opacity: 1 }))], { optional: true })
		]),
		query(':enter', animateChild(), { optional: true })
	])
]);
