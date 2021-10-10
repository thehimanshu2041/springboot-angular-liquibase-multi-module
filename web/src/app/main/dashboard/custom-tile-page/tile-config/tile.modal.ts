import { Observable } from 'rxjs';
import { InjectionToken } from '@angular/core';
import { ManagedContent } from 'src/app/generated/rest';

export const TILE_SPECS = [[1, 1], [2, 1], [1, 2], [2, 2], [1, 3], [2, 3], [3]];
export const CONTENT_TILE_CONTEXT = new InjectionToken<ContentTileContext>('content.tile.context');


export interface ManagedContentWithCtx extends ManagedContent {
    context?: ContentTileContext;
}

export interface ContentTileContext {
    key?: string;
    index?: number;
    type?: string;
    expandable?: boolean;
    expanded?: boolean;
    framed?: boolean;
    title?: string;
    allTiles?: string[];
    hidden?: boolean;
    tileCode?: string;
    fireEvent?(tile: string, event: string): Promise<any>;
    watchEvents?(): Observable<ContentTileEvent>;
}

export class ContentTileEvent {
    constructor(
        public readonly tileKey: string,
        public readonly event: string,
        public readonly resultResolver: (value: any) => void
    ) { }
}
