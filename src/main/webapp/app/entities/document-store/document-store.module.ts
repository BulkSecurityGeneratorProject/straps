import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    DocumentStoreComponent,
    DocumentStoreDetailComponent,
    DocumentStoreUpdateComponent,
    DocumentStoreDeletePopupComponent,
    DocumentStoreDeleteDialogComponent,
    documentStoreRoute,
    documentStorePopupRoute
} from './';

const ENTITY_STATES = [...documentStoreRoute, ...documentStorePopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DocumentStoreComponent,
        DocumentStoreDetailComponent,
        DocumentStoreUpdateComponent,
        DocumentStoreDeleteDialogComponent,
        DocumentStoreDeletePopupComponent
    ],
    entryComponents: [
        DocumentStoreComponent,
        DocumentStoreUpdateComponent,
        DocumentStoreDeleteDialogComponent,
        DocumentStoreDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5DocumentStoreModule {}
