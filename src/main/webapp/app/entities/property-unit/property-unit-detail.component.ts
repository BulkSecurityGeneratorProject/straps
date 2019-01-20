import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPropertyUnit } from 'app/shared/model/property-unit.model';

@Component({
    selector: 'jhi-property-unit-detail',
    templateUrl: './property-unit-detail.component.html'
})
export class PropertyUnitDetailComponent implements OnInit {
    propertyUnit: IPropertyUnit;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ propertyUnit }) => {
            this.propertyUnit = propertyUnit;
        });
    }

    previousState() {
        window.history.back();
    }
}
