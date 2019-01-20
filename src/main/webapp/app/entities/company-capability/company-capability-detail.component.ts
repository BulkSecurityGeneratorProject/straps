import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyCapability } from 'app/shared/model/company-capability.model';

@Component({
    selector: 'jhi-company-capability-detail',
    templateUrl: './company-capability-detail.component.html'
})
export class CompanyCapabilityDetailComponent implements OnInit {
    companyCapability: ICompanyCapability;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ companyCapability }) => {
            this.companyCapability = companyCapability;
        });
    }

    previousState() {
        window.history.back();
    }
}
