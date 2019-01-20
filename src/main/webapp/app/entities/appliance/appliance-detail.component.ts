import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppliance } from 'app/shared/model/appliance.model';

@Component({
    selector: 'jhi-appliance-detail',
    templateUrl: './appliance-detail.component.html'
})
export class ApplianceDetailComponent implements OnInit {
    appliance: IAppliance;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appliance }) => {
            this.appliance = appliance;
        });
    }

    previousState() {
        window.history.back();
    }
}
