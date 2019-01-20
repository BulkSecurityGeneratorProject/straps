/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { PropertyVendorUpdateComponent } from 'app/entities/property-vendor/property-vendor-update.component';
import { PropertyVendorService } from 'app/entities/property-vendor/property-vendor.service';
import { PropertyVendor } from 'app/shared/model/property-vendor.model';

describe('Component Tests', () => {
    describe('PropertyVendor Management Update Component', () => {
        let comp: PropertyVendorUpdateComponent;
        let fixture: ComponentFixture<PropertyVendorUpdateComponent>;
        let service: PropertyVendorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [PropertyVendorUpdateComponent]
            })
                .overrideTemplate(PropertyVendorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PropertyVendorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyVendorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PropertyVendor(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.propertyVendor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PropertyVendor();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.propertyVendor = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
