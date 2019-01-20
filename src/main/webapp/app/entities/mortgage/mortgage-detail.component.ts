import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMortgage } from 'app/shared/model/mortgage.model';

@Component({
    selector: 'jhi-mortgage-detail',
    templateUrl: './mortgage-detail.component.html'
})
export class MortgageDetailComponent implements OnInit {
    mortgage: IMortgage;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mortgage }) => {
            this.mortgage = mortgage;
        });
    }

    previousState() {
        window.history.back();
    }
}
