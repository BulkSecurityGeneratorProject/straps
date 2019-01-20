import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PromalyV5PortfolioModule } from './portfolio/portfolio.module';
import { PromalyV5PropertyModule } from './property/property.module';
import { PromalyV5BuildingModule } from './building/building.module';
import { PromalyV5PropertyUnitModule } from './property-unit/property-unit.module';
import { PromalyV5AddressModule } from './address/address.module';
import { PromalyV5ApplianceModule } from './appliance/appliance.module';
import { PromalyV5WarrantyModule } from './warranty/warranty.module';
import { PromalyV5ContractModule } from './contract/contract.module';
import { PromalyV5FeatureModule } from './feature/feature.module';
import { PromalyV5ContactModule } from './contact/contact.module';
import { PromalyV5ExpenseModule } from './expense/expense.module';
import { PromalyV5InvoiceHeaderModule } from './invoice-header/invoice-header.module';
import { PromalyV5InvoiceLineModule } from './invoice-line/invoice-line.module';
import { PromalyV5BillingPlanModule } from './billing-plan/billing-plan.module';
import { PromalyV5DiscountPlanModule } from './discount-plan/discount-plan.module';
import { PromalyV5LandLordModule } from './land-lord/land-lord.module';
import { PromalyV5LeaseModule } from './lease/lease.module';
import { PromalyV5LookupModule } from './lookup/lookup.module';
import { PromalyV5LookupTypeModule } from './lookup-type/lookup-type.module';
import { PromalyV5MortgageModule } from './mortgage/mortgage.module';
import { PromalyV5PropertyVendorModule } from './property-vendor/property-vendor.module';
import { PromalyV5ServiceRequestModule } from './service-request/service-request.module';
import { PromalyV5TenantModule } from './tenant/tenant.module';
import { PromalyV5CompanyCapabilityModule } from './company-capability/company-capability.module';
import { PromalyV5RentRollModule } from './rent-roll/rent-roll.module';
import { PromalyV5NotesModule } from './notes/notes.module';
import { PromalyV5AmenityModule } from './amenity/amenity.module';
import { PromalyV5DocumentStoreModule } from './document-store/document-store.module';
import { PromalyV5IncomeProjectionModule } from './income-projection/income-projection.module';
import { PromalyV5CompanyModule } from './company/company.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PromalyV5PortfolioModule,
        PromalyV5PropertyModule,
        PromalyV5BuildingModule,
        PromalyV5PropertyUnitModule,
        PromalyV5AddressModule,
        PromalyV5ApplianceModule,
        PromalyV5WarrantyModule,
        PromalyV5ContractModule,
        PromalyV5FeatureModule,
        PromalyV5ContactModule,
        PromalyV5ExpenseModule,
        PromalyV5InvoiceHeaderModule,
        PromalyV5InvoiceLineModule,
        PromalyV5BillingPlanModule,
        PromalyV5DiscountPlanModule,
        PromalyV5LandLordModule,
        PromalyV5LeaseModule,
        PromalyV5LookupModule,
        PromalyV5LookupTypeModule,
        PromalyV5MortgageModule,
        PromalyV5PropertyVendorModule,
        PromalyV5ServiceRequestModule,
        PromalyV5TenantModule,
        PromalyV5CompanyCapabilityModule,
        PromalyV5RentRollModule,
        PromalyV5NotesModule,
        PromalyV5AmenityModule,
        PromalyV5DocumentStoreModule,
        PromalyV5IncomeProjectionModule,
        PromalyV5CompanyModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PromalyV5EntityModule {}
