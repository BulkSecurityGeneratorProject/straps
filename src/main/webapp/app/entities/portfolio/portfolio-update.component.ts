import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPortfolio } from 'app/shared/model/portfolio.model';
import { PortfolioService } from './portfolio.service';

@Component({
    selector: 'jhi-portfolio-update',
    templateUrl: './portfolio-update.component.html'
})
export class PortfolioUpdateComponent implements OnInit {
    portfolio: IPortfolio;
    isSaving: boolean;

    constructor(protected portfolioService: PortfolioService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ portfolio }) => {
            this.portfolio = portfolio;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.portfolio.id !== undefined) {
            this.subscribeToSaveResponse(this.portfolioService.update(this.portfolio));
        } else {
            this.subscribeToSaveResponse(this.portfolioService.create(this.portfolio));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPortfolio>>) {
        result.subscribe((res: HttpResponse<IPortfolio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
