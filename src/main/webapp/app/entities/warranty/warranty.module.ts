import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    WarrantyComponent,
    WarrantyDetailComponent,
    WarrantyUpdateComponent,
    WarrantyDeletePopupComponent,
    WarrantyDeleteDialogComponent,
    warrantyRoute,
    warrantyPopupRoute
} from './';

const ENTITY_STATES = [...warrantyRoute, ...warrantyPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WarrantyComponent,
        WarrantyDetailComponent,
        WarrantyUpdateComponent,
        WarrantyDeleteDialogComponent,
        WarrantyDeletePopupComponent
    ],
    entryComponents: [WarrantyComponent, WarrantyUpdateComponent, WarrantyDeleteDialogComponent, WarrantyDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5WarrantyModule {}
