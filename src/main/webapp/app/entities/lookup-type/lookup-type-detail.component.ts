import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILookupType } from 'app/shared/model/lookup-type.model';

@Component({
    selector: 'jhi-lookup-type-detail',
    templateUrl: './lookup-type-detail.component.html'
})
export class LookupTypeDetailComponent implements OnInit {
    lookupType: ILookupType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lookupType }) => {
            this.lookupType = lookupType;
        });
    }

    previousState() {
        window.history.back();
    }
}
