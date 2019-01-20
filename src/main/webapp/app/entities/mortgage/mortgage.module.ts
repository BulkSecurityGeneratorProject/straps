import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    MortgageComponent,
    MortgageDetailComponent,
    MortgageUpdateComponent,
    MortgageDeletePopupComponent,
    MortgageDeleteDialogComponent,
    mortgageRoute,
    mortgagePopupRoute
} from './';

const ENTITY_STATES = [...mortgageRoute, ...mortgagePopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MortgageComponent,
        MortgageDetailComponent,
        MortgageUpdateComponent,
        MortgageDeleteDialogComponent,
        MortgageDeletePopupComponent
    ],
    entryComponents: [MortgageComponent, MortgageUpdateComponent, MortgageDeleteDialogComponent, MortgageDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5MortgageModule {}
