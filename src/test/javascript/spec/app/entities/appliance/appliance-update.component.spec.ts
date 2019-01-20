/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { ApplianceUpdateComponent } from 'app/entities/appliance/appliance-update.component';
import { ApplianceService } from 'app/entities/appliance/appliance.service';
import { Appliance } from 'app/shared/model/appliance.model';

describe('Component Tests', () => {
    describe('Appliance Management Update Component', () => {
        let comp: ApplianceUpdateComponent;
        let fixture: ComponentFixture<ApplianceUpdateComponent>;
        let service: ApplianceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [ApplianceUpdateComponent]
            })
                .overrideTemplate(ApplianceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApplianceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplianceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Appliance(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appliance = entity;
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
                    const entity = new Appliance();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appliance = entity;
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
