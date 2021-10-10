import { Injectable } from '@angular/core';
import { TranslateLoader } from '@ngx-translate/core';
import * as _ from 'lodash';
import { Observable, of } from 'rxjs';

// @ts-ignore
const enContext = require.context('../../', true, /(^|\/|\\|\.)en\.json$/);
const enFiles = enContext.keys().map(enContext);

const translationFiles: { [key: string]: any } = {
	en: enFiles
};

@Injectable()
export class StaticTranslateLoader implements TranslateLoader {
	public getTranslation(lang: string): Observable<any> {
		let trans = translationFiles[lang];
		return of(_.isArray(trans) ? _.merge.apply(_, trans) : trans);
	}
}
