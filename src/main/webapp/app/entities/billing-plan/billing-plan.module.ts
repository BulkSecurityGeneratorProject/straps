import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    BillingPlanComponent,
    BillingPlanDetailComponent,
    BillingPlanUpdateComponent,
    BillingPlanDeletePopupComponent,
    BillingPlanDeleteDialogComponent,
    billingPlanRoute,
    billingPlanPopupRoute
} from './';

const ENTITY_STATES = [...billingPlanRoute, ...billingPlanPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BillingPlanComponent,
        BillingPlanDetailComponent,
        BillingPlanUpdateComponent,
        BillingPlanDeleteDialogComponent,
        BillingPlanDeletePopupComponent
    ],
    entryComponents: [BillingPlanComponent, BillingPlanUpdateComponent, BillingPlanDeleteDialogComponent, BillingPlanDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5BillingPlanModule {}
