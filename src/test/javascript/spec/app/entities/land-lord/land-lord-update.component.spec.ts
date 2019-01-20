/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { LandLordUpdateComponent } from 'app/entities/land-lord/land-lord-update.component';
import { LandLordService } from 'app/entities/land-lord/land-lord.service';
import { LandLord } from 'app/shared/model/land-lord.model';

describe('Component Tests', () => {
    describe('LandLord Management Update Component', () => {
        let comp: LandLordUpdateComponent;
        let fixture: ComponentFixture<LandLordUpdateComponent>;
        let service: LandLordService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [LandLordUpdateComponent]
            })
                .overrideTemplate(LandLordUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LandLordUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LandLordService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LandLord(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.landLord = entity;
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
                    const entity = new LandLord();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.landLord = entity;
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
