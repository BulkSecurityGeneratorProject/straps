import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    ExpenseComponent,
    ExpenseDetailComponent,
    ExpenseUpdateComponent,
    ExpenseDeletePopupComponent,
    ExpenseDeleteDialogComponent,
    expenseRoute,
    expensePopupRoute
} from './';

const ENTITY_STATES = [...expenseRoute, ...expensePopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExpenseComponent,
        ExpenseDetailComponent,
        ExpenseUpdateComponent,
        ExpenseDeleteDialogComponent,
        ExpenseDeletePopupComponent
    ],
    entryComponents: [ExpenseComponent, ExpenseUpdateComponent, ExpenseDeleteDialogComponent, ExpenseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5ExpenseModule {}
