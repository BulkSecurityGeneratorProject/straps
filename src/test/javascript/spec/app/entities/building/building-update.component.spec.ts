/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { BuildingUpdateComponent } from 'app/entities/building/building-update.component';
import { BuildingService } from 'app/entities/building/building.service';
import { Building } from 'app/shared/model/building.model';

describe('Component Tests', () => {
    describe('Building Management Update Component', () => {
        let comp: BuildingUpdateComponent;
        let fixture: ComponentFixture<BuildingUpdateComponent>;
        let service: BuildingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [BuildingUpdateComponent]
            })
                .overrideTemplate(BuildingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BuildingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BuildingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Building(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.building = entity;
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
                    const entity = new Building();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.building = entity;
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
