import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    InvoiceHeaderComponent,
    InvoiceHeaderDetailComponent,
    InvoiceHeaderUpdateComponent,
    InvoiceHeaderDeletePopupComponent,
    InvoiceHeaderDeleteDialogComponent,
    invoiceHeaderRoute,
    invoiceHeaderPopupRoute
} from './';

const ENTITY_STATES = [...invoiceHeaderRoute, ...invoiceHeaderPopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvoiceHeaderComponent,
        InvoiceHeaderDetailComponent,
        InvoiceHeaderUpdateComponent,
        InvoiceHeaderDeleteDialogComponent,
        InvoiceHeaderDeletePopupComponent
    ],
    entryComponents: [
        InvoiceHeaderComponent,
        InvoiceHeaderUpdateComponent,
        InvoiceHeaderDeleteDialogComponent,
        InvoiceHeaderDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5InvoiceHeaderModule {}
