import { ExistingProvider } from '@angular/core';
import {
	TranslateLoader,
	TranslateModuleConfig
} from '@ngx-translate/core';

import { StaticTranslateLoader } from './static.translate.loader';

const translateLoaderProvider: ExistingProvider = {
	provide: TranslateLoader,
	useExisting: StaticTranslateLoader
};

export const translateConfiguration: TranslateModuleConfig = {
	loader: translateLoaderProvider
};
