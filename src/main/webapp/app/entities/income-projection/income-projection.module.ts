import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    IncomeProjectionComponent,
    IncomeProjectionDetailComponent,
    IncomeProjectionUpdateComponent,
    IncomeProjectionDeletePopupComponent,
    IncomeProjectionDeleteDialogComponent,
    incomeProjectionRoute,
    incomeProjectionPopupRoute
} from './';

const ENTITY_STATES = [...incomeProjectionRoute, ...incomeProjectionPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IncomeProjectionComponent,
        IncomeProjectionDetailComponent,
        IncomeProjectionUpdateComponent,
        IncomeProjectionDeleteDialogComponent,
        IncomeProjectionDeletePopupComponent
    ],
    entryComponents: [
        IncomeProjectionComponent,
        IncomeProjectionUpdateComponent,
        IncomeProjectionDeleteDialogComponent,
        IncomeProjectionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5IncomeProjectionModule {}
