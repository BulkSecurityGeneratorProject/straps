import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    ApplianceComponent,
    ApplianceDetailComponent,
    ApplianceUpdateComponent,
    ApplianceDeletePopupComponent,
    ApplianceDeleteDialogComponent,
    applianceRoute,
    appliancePopupRoute
} from './';

const ENTITY_STATES = [...applianceRoute, ...appliancePopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApplianceComponent,
        ApplianceDetailComponent,
        ApplianceUpdateComponent,
        ApplianceDeleteDialogComponent,
        ApplianceDeletePopupComponent
    ],
    entryComponents: [ApplianceComponent, ApplianceUpdateComponent, ApplianceDeleteDialogComponent, ApplianceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5ApplianceModule {}
