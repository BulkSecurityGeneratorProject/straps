import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    AmenityComponent,
    AmenityDetailComponent,
    AmenityUpdateComponent,
    AmenityDeletePopupComponent,
    AmenityDeleteDialogComponent,
    amenityRoute,
    amenityPopupRoute
} from './';

const ENTITY_STATES = [...amenityRoute, ...amenityPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AmenityComponent,
        AmenityDetailComponent,
        AmenityUpdateComponent,
        AmenityDeleteDialogComponent,
        AmenityDeletePopupComponent
    ],
    entryComponents: [AmenityComponent, AmenityUpdateComponent, AmenityDeleteDialogComponent, AmenityDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5AmenityModule {}
