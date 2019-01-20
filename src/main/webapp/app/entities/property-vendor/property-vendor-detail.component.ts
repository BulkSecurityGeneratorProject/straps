import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPropertyVendor } from 'app/shared/model/property-vendor.model';

@Component({
    selector: 'jhi-property-vendor-detail',
    templateUrl: './property-vendor-detail.component.html'
})
export class PropertyVendorDetailComponent implements OnInit {
    propertyVendor: IPropertyVendor;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ propertyVendor }) => {
            this.propertyVendor = propertyVendor;
        });
    }

    previousState() {
        window.history.back();
    }
}
