import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    BuildingComponent,
    BuildingDetailComponent,
    BuildingUpdateComponent,
    BuildingDeletePopupComponent,
    BuildingDeleteDialogComponent,
    buildingRoute,
    buildingPopupRoute
} from './';

const ENTITY_STATES = [...buildingRoute, ...buildingPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BuildingComponent,
        BuildingDetailComponent,
        BuildingUpdateComponent,
        BuildingDeleteDialogComponent,
        BuildingDeletePopupComponent
    ],
    entryComponents: [BuildingComponent, BuildingUpdateComponent, BuildingDeleteDialogComponent, BuildingDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5BuildingModule {}
