import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    CompanyCapabilityComponent,
    CompanyCapabilityDetailComponent,
    CompanyCapabilityUpdateComponent,
    CompanyCapabilityDeletePopupComponent,
    CompanyCapabilityDeleteDialogComponent,
    companyCapabilityRoute,
    companyCapabilityPopupRoute
} from './';

const ENTITY_STATES = [...companyCapabilityRoute, ...companyCapabilityPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompanyCapabilityComponent,
        CompanyCapabilityDetailComponent,
        CompanyCapabilityUpdateComponent,
        CompanyCapabilityDeleteDialogComponent,
        CompanyCapabilityDeletePopupComponent
    ],
    entryComponents: [
        CompanyCapabilityComponent,
        CompanyCapabilityUpdateComponent,
        CompanyCapabilityDeleteDialogComponent,
        CompanyCapabilityDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5CompanyCapabilityModule {}
