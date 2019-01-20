import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRentRoll } from 'app/shared/model/rent-roll.model';

@Component({
    selector: 'jhi-rent-roll-detail',
    templateUrl: './rent-roll-detail.component.html'
})
export class RentRollDetailComponent implements OnInit {
    rentRoll: IRentRoll;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rentRoll }) => {
            this.rentRoll = rentRoll;
        });
    }

    previousState() {
        window.history.back();
    }
}
