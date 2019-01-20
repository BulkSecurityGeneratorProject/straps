/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { AmenityUpdateComponent } from 'app/entities/amenity/amenity-update.component';
import { AmenityService } from 'app/entities/amenity/amenity.service';
import { Amenity } from 'app/shared/model/amenity.model';

describe('Component Tests', () => {
    describe('Amenity Management Update Component', () => {
        let comp: AmenityUpdateComponent;
        let fixture: ComponentFixture<AmenityUpdateComponent>;
        let service: AmenityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [AmenityUpdateComponent]
            })
                .overrideTemplate(AmenityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AmenityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmenityService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Amenity(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.amenity = entity;
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
                    const entity = new Amenity();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.amenity = entity;
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
