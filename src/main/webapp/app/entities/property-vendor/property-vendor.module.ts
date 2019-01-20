import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    PropertyVendorComponent,
    PropertyVendorDetailComponent,
    PropertyVendorUpdateComponent,
    PropertyVendorDeletePopupComponent,
    PropertyVendorDeleteDialogComponent,
    propertyVendorRoute,
    propertyVendorPopupRoute
} from './';

const ENTITY_STATES = [...propertyVendorRoute, ...propertyVendorPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PropertyVendorComponent,
        PropertyVendorDetailComponent,
        PropertyVendorUpdateComponent,
        PropertyVendorDeleteDialogComponent,
        PropertyVendorDeletePopupComponent
    ],
    entryComponents: [
        PropertyVendorComponent,
        PropertyVendorUpdateComponent,
        PropertyVendorDeleteDialogComponent,
        PropertyVendorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5PropertyVendorModule {}
