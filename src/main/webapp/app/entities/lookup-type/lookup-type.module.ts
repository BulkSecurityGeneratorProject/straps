import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    LookupTypeComponent,
    LookupTypeDetailComponent,
    LookupTypeUpdateComponent,
    LookupTypeDeletePopupComponent,
    LookupTypeDeleteDialogComponent,
    lookupTypeRoute,
    lookupTypePopupRoute
} from './';

const ENTITY_STATES = [...lookupTypeRoute, ...lookupTypePopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LookupTypeComponent,
        LookupTypeDetailComponent,
        LookupTypeUpdateComponent,
        LookupTypeDeleteDialogComponent,
        LookupTypeDeletePopupComponent
    ],
    entryComponents: [LookupTypeComponent, LookupTypeUpdateComponent, LookupTypeDeleteDialogComponent, LookupTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5LookupTypeModule {}
