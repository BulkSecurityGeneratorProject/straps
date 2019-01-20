/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { PropertyVendorDetailComponent } from 'app/entities/property-vendor/property-vendor-detail.component';
import { PropertyVendor } from 'app/shared/model/property-vendor.model';

describe('Component Tests', () => {
    describe('PropertyVendor Management Detail Component', () => {
        let comp: PropertyVendorDetailComponent;
        let fixture: ComponentFixture<PropertyVendorDetailComponent>;
        const route = ({ data: of({ propertyVendor: new PropertyVendor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [PropertyVendorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PropertyVendorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PropertyVendorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.propertyVendor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
