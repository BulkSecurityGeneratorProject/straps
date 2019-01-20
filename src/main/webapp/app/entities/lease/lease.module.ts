import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    LeaseComponent,
    LeaseDetailComponent,
    LeaseUpdateComponent,
    LeaseDeletePopupComponent,
    LeaseDeleteDialogComponent,
    leaseRoute,
    leasePopupRoute
} from './';

const ENTITY_STATES = [...leaseRoute, ...leasePopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LeaseComponent, LeaseDetailComponent, LeaseUpdateComponent, LeaseDeleteDialogComponent, LeaseDeletePopupComponent],
    entryComponents: [LeaseComponent, LeaseUpdateComponent, LeaseDeleteDialogComponent, LeaseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5LeaseModule {}
