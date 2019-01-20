import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncomeProjection } from 'app/shared/model/income-projection.model';

@Component({
    selector: 'jhi-income-projection-detail',
    templateUrl: './income-projection-detail.component.html'
})
export class IncomeProjectionDetailComponent implements OnInit {
    incomeProjection: IIncomeProjection;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incomeProjection }) => {
            this.incomeProjection = incomeProjection;
        });
    }

    previousState() {
        window.history.back();
    }
}
