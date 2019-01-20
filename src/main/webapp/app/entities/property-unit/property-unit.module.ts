import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    PropertyUnitComponent,
    PropertyUnitDetailComponent,
    PropertyUnitUpdateComponent,
    PropertyUnitDeletePopupComponent,
    PropertyUnitDeleteDialogComponent,
    propertyUnitRoute,
    propertyUnitPopupRoute
} from './';

const ENTITY_STATES = [...propertyUnitRoute, ...propertyUnitPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PropertyUnitComponent,
        PropertyUnitDetailComponent,
        PropertyUnitUpdateComponent,
        PropertyUnitDeleteDialogComponent,
        PropertyUnitDeletePopupComponent
    ],
    entryComponents: [
        PropertyUnitComponent,
        PropertyUnitUpdateComponent,
        PropertyUnitDeleteDialogComponent,
        PropertyUnitDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5PropertyUnitModule {}
