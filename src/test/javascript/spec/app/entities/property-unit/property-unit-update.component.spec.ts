/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { PropertyUnitUpdateComponent } from 'app/entities/property-unit/property-unit-update.component';
import { PropertyUnitService } from 'app/entities/property-unit/property-unit.service';
import { PropertyUnit } from 'app/shared/model/property-unit.model';

describe('Component Tests', () => {
    describe('PropertyUnit Management Update Component', () => {
        let comp: PropertyUnitUpdateComponent;
        let fixture: ComponentFixture<PropertyUnitUpdateComponent>;
        let service: PropertyUnitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [PropertyUnitUpdateComponent]
            })
                .overrideTemplate(PropertyUnitUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PropertyUnitUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PropertyUnitService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PropertyUnit(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.propertyUnit = entity;
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
                    const entity = new PropertyUnit();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.propertyUnit = entity;
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
