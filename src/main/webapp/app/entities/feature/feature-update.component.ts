import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFeature } from 'app/shared/model/feature.model';
import { FeatureService } from './feature.service';
import { ILookup } from 'app/shared/model/lookup.model';
import { LookupService } from 'app/entities/lookup';

@Component({
    selector: 'jhi-feature-update',
    templateUrl: './feature-update.component.html'
})
export class FeatureUpdateComponent implements OnInit {
    feature: IFeature;
    isSaving: boolean;

    types: ILookup[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected featureService: FeatureService,
        protected lookupService: LookupService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ feature }) => {
            this.feature = feature;
        });
        this.lookupService.query({ filter: 'feature-is-null' }).subscribe(
            (res: HttpResponse<ILookup[]>) => {
                if (!this.feature.typeId) {
                    this.types = res.body;
                } else {
                    this.lookupService.find(this.feature.typeId).subscribe(
                        (subRes: HttpResponse<ILookup>) => {
                            this.types = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.feature.id !== undefined) {
            this.subscribeToSaveResponse(this.featureService.update(this.feature));
        } else {
            this.subscribeToSaveResponse(this.featureService.create(this.feature));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeature>>) {
        result.subscribe((res: HttpResponse<IFeature>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLookupById(index: number, item: ILookup) {
        return item.id;
    }
}
