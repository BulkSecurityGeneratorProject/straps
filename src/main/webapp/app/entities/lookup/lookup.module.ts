import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    LookupComponent,
    LookupDetailComponent,
    LookupUpdateComponent,
    LookupDeletePopupComponent,
    LookupDeleteDialogComponent,
    lookupRoute,
    lookupPopupRoute
} from './';

const ENTITY_STATES = [...lookupRoute, ...lookupPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LookupComponent, LookupDetailComponent, LookupUpdateComponent, LookupDeleteDialogComponent, LookupDeletePopupComponent],
    entryComponents: [LookupComponent, LookupUpdateComponent, LookupDeleteDialogComponent, LookupDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5LookupModule {}
