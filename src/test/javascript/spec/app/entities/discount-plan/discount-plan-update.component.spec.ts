/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { DiscountPlanUpdateComponent } from 'app/entities/discount-plan/discount-plan-update.component';
import { DiscountPlanService } from 'app/entities/discount-plan/discount-plan.service';
import { DiscountPlan } from 'app/shared/model/discount-plan.model';

describe('Component Tests', () => {
    describe('DiscountPlan Management Update Component', () => {
        let comp: DiscountPlanUpdateComponent;
        let fixture: ComponentFixture<DiscountPlanUpdateComponent>;
        let service: DiscountPlanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [DiscountPlanUpdateComponent]
            })
                .overrideTemplate(DiscountPlanUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DiscountPlanUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiscountPlanService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DiscountPlan(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.discountPlan = entity;
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
                    const entity = new DiscountPlan();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.discountPlan = entity;
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
