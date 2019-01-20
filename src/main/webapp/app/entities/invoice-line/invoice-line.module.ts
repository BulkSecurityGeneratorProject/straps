import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PromalyV5SharedModule } from 'app/shared';
import {
    InvoiceLineComponent,
    InvoiceLineDetailComponent,
    InvoiceLineUpdateComponent,
    InvoiceLineDeletePopupComponent,
    InvoiceLineDeleteDialogComponent,
    invoiceLineRoute,
    invoiceLinePopupRoute
} from './';

const ENTITY_STATES = [...invoiceLineRoute, ...invoiceLinePopupRoute];

@NgModule({
    imports: [PromalyV5SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InvoiceLineComponent,
        InvoiceLineDetailComponent,
        InvoiceLineUpdateComponent,
        InvoiceLineDeleteDialogComponent,
        InvoiceLineDeletePopupComponent
    ],
    entryComponents: [InvoiceLineComponent, InvoiceLineUpdateComponent, InvoiceLineDeleteDialogComponent, InvoiceLineDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5InvoiceLineModule {}
