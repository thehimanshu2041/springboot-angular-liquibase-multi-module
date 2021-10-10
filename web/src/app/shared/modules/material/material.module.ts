import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatSelectModule } from '@angular/material/select';
import { MatSliderModule } from '@angular/material/slider';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatRadioModule } from '@angular/material/radio';
import { MatChipsModule } from '@angular/material/chips';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatBadgeModule } from '@angular/material/badge';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatStepperModule } from '@angular/material/stepper';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatExpansionModule } from '@angular/material/expansion';
import { DateAdapter, MatNativeDateModule, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTreeModule } from '@angular/material/tree';
import {
  MatFormFieldDefaultOptions,
  MatFormFieldModule,
  MAT_FORM_FIELD_DEFAULT_OPTIONS
} from '@angular/material/form-field';
import { CdkStepperModule } from '@angular/cdk/stepper';
import {
  MomentDateAdapter
} from "@angular/material-moment-adapter";

const DECLARE_MAT_MODULE = [MatFormFieldModule, MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatCheckboxModule,
  MatListModule, MatCardModule, MatButtonToggleModule, MatSelectModule, MatMenuModule, MatProgressSpinnerModule, MatProgressBarModule,
  MatInputModule, MatTabsModule, MatGridListModule, MatAutocompleteModule, MatRadioModule, MatChipsModule, MatDialogModule,
  MatDividerModule, MatSnackBarModule, MatBadgeModule, MatSlideToggleModule,
  MatTooltipModule, MatTableModule, MatStepperModule, MatDatepickerModule, MatExpansionModule, MatTreeModule, MatDialogModule,
  MatFormFieldModule, MatNativeDateModule, MatSliderModule, CdkStepperModule
];


export const MY_FORMATS = {
  parse: { dateInput: 'DD/MM/YYYY' },
  display: {
    dateInput: 'DD/MM/YYYY',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM-YYYY'
  }
};

const formFieldDefaultOptions: MatFormFieldDefaultOptions = {
  appearance: 'outline'
};

@NgModule({

  imports: [DECLARE_MAT_MODULE],
  exports: [DECLARE_MAT_MODULE],
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: formFieldDefaultOptions
    },
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE],
    },
    {
      provide: MAT_DATE_FORMATS,
      useValue: MY_FORMATS
    }
  ]
})
export class MaterialModule { }
