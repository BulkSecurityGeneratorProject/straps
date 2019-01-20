import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILandLord } from 'app/shared/model/land-lord.model';

@Component({
    selector: 'jhi-land-lord-detail',
    templateUrl: './land-lord-detail.component.html'
})
export class LandLordDetailComponent implements OnInit {
    landLord: ILandLord;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ landLord }) => {
            this.landLord = landLord;
        });
    }

    previousState() {
        window.history.back();
    }
}
