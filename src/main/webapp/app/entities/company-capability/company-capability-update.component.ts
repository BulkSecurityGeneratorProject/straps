import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICompanyCapability } from 'app/shared/model/company-capability.model';
import { CompanyCapabilityService } from './company-capability.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
    selector: 'jhi-company-capability-update',
    templateUrl: './company-capability-update.component.html'
})
export class CompanyCapabilityUpdateComponent implements OnInit {
    companyCapability: ICompanyCapability;
    isSaving: boolean;

    companies: ICompany[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected companyCapabilityService: CompanyCapabilityService,
        protected companyService: CompanyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ companyCapability }) => {
            this.companyCapability = companyCapability;
        });
        this.companyService.query().subscribe(
            (res: HttpResponse<ICompany[]>) => {
                this.companies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.companyCapability.id !== undefined) {
            this.subscribeToSaveResponse(this.companyCapabilityService.update(this.companyCapability));
        } else {
            this.subscribeToSaveResponse(this.companyCapabilityService.create(this.companyCapability));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyCapability>>) {
        result.subscribe((res: HttpResponse<ICompanyCapability>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCompanyById(index: number, item: ICompany) {
        return item.id;
    }
}
