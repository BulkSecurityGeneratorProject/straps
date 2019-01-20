import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    RentRollComponent,
    RentRollDetailComponent,
    RentRollUpdateComponent,
    RentRollDeletePopupComponent,
    RentRollDeleteDialogComponent,
    rentRollRoute,
    rentRollPopupRoute
} from './';

const ENTITY_STATES = [...rentRollRoute, ...rentRollPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RentRollComponent,
        RentRollDetailComponent,
        RentRollUpdateComponent,
        RentRollDeleteDialogComponent,
        RentRollDeletePopupComponent
    ],
    entryComponents: [RentRollComponent, RentRollUpdateComponent, RentRollDeleteDialogComponent, RentRollDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5RentRollModule {}
