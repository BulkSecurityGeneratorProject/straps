import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    LandLordComponent,
    LandLordDetailComponent,
    LandLordUpdateComponent,
    LandLordDeletePopupComponent,
    LandLordDeleteDialogComponent,
    landLordRoute,
    landLordPopupRoute
} from './';

const ENTITY_STATES = [...landLordRoute, ...landLordPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LandLordComponent,
        LandLordDetailComponent,
        LandLordUpdateComponent,
        LandLordDeleteDialogComponent,
        LandLordDeletePopupComponent
    ],
    entryComponents: [LandLordComponent, LandLordUpdateComponent, LandLordDeleteDialogComponent, LandLordDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5LandLordModule {}
