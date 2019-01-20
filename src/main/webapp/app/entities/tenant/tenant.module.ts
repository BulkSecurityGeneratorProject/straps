import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    TenantComponent,
    TenantDetailComponent,
    TenantUpdateComponent,
    TenantDeletePopupComponent,
    TenantDeleteDialogComponent,
    tenantRoute,
    tenantPopupRoute
} from './';

const ENTITY_STATES = [...tenantRoute, ...tenantPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TenantComponent, TenantDetailComponent, TenantUpdateComponent, TenantDeleteDialogComponent, TenantDeletePopupComponent],
    entryComponents: [TenantComponent, TenantUpdateComponent, TenantDeleteDialogComponent, TenantDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5TenantModule {}
