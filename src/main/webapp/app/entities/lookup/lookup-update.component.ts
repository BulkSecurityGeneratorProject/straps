import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from './lookup.service';
import { IContract } from 'app/shared/model/contract.model';
import { ContractService } from 'app/entities/contract';

@Component({
    selector: 'jhi-lookup-update',
    templateUrl: './lookup-update.component.html'
})
export class LookupUpdateComponent implements OnInit {
    lookup: ILookup;
    isSaving: boolean;

    contracts: IContract[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected lookupService: LookupService,
        protected contractService: ContractService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lookup }) => {
            this.lookup = lookup;
        });
        this.contractService.query().subscribe(
            (res: HttpResponse<IContract[]>) => {
                this.contracts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lookup.id !== undefined) {
            this.subscribeToSaveResponse(this.lookupService.update(this.lookup));
        } else {
            this.subscribeToSaveResponse(this.lookupService.create(this.lookup));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILookup>>) {
        result.subscribe((res: HttpResponse<ILookup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackContractById(index: number, item: IContract) {
        return item.id;
    }
}
