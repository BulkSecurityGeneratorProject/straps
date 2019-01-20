/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { IncomeProjectionUpdateComponent } from 'app/entities/income-projection/income-projection-update.component';
import { IncomeProjectionService } from 'app/entities/income-projection/income-projection.service';
import { IncomeProjection } from 'app/shared/model/income-projection.model';

describe('Component Tests', () => {
    describe('IncomeProjection Management Update Component', () => {
        let comp: IncomeProjectionUpdateComponent;
        let fixture: ComponentFixture<IncomeProjectionUpdateComponent>;
        let service: IncomeProjectionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [IncomeProjectionUpdateComponent]
            })
                .overrideTemplate(IncomeProjectionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IncomeProjectionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncomeProjectionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new IncomeProjection(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.incomeProjection = entity;
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
                    const entity = new IncomeProjection();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.incomeProjection = entity;
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
