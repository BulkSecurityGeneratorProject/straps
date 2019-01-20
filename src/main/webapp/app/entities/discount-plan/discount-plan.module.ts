import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    DiscountPlanComponent,
    DiscountPlanDetailComponent,
    DiscountPlanUpdateComponent,
    DiscountPlanDeletePopupComponent,
    DiscountPlanDeleteDialogComponent,
    discountPlanRoute,
    discountPlanPopupRoute
} from './';

const ENTITY_STATES = [...discountPlanRoute, ...discountPlanPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DiscountPlanComponent,
        DiscountPlanDetailComponent,
        DiscountPlanUpdateComponent,
        DiscountPlanDeleteDialogComponent,
        DiscountPlanDeletePopupComponent
    ],
    entryComponents: [
        DiscountPlanComponent,
        DiscountPlanUpdateComponent,
        DiscountPlanDeleteDialogComponent,
        DiscountPlanDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5DiscountPlanModule {}
