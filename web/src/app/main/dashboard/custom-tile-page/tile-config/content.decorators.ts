import { Type, TypeDecorator } from '@angular/core';

interface ContentTileDecorator {
	new (obj: ContentTile): ContentTile;
	(obj: ContentTile): TypeDecorator;
}

interface ContentTileMetaBase {
	icon?: string;
}

interface ContentTile extends ContentTileMetaBase {
	key: string;
}

interface ContentTileMeta extends ContentTileMetaBase {
	__class: Type<any>;
}

export const contentTileRegistry: { [key: string]: ContentTileMeta } = {};

export function ContentTileDecoratorFactory(props: ContentTile) {
	if (!props.key) {
		throw new Error('A valid content key must be provided!');
	}
	return function TypeDecorator(cls: Type<any>) {
		if (contentTileRegistry[props.key]) {
			throw new Error(`Content key ${props.key} has already been registered!`);
		}

		const item: ContentTileMeta = {
			__class: cls
		};
		Object.getOwnPropertyNames(props).forEach(n => (item[n] = props[n]));
		contentTileRegistry[props.key] = item;
		return cls;
	};
}

export const ContentTile: ContentTileDecorator = ContentTileDecoratorFactory as any;
