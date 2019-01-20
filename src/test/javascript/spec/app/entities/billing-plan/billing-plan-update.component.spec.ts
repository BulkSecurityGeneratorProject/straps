/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { BillingPlanUpdateComponent } from 'app/entities/billing-plan/billing-plan-update.component';
import { BillingPlanService } from 'app/entities/billing-plan/billing-plan.service';
import { BillingPlan } from 'app/shared/model/billing-plan.model';

describe('Component Tests', () => {
    describe('BillingPlan Management Update Component', () => {
        let comp: BillingPlanUpdateComponent;
        let fixture: ComponentFixture<BillingPlanUpdateComponent>;
        let service: BillingPlanService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [BillingPlanUpdateComponent]
            })
                .overrideTemplate(BillingPlanUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BillingPlanUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingPlanService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BillingPlan(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.billingPlan = entity;
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
                    const entity = new BillingPlan();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.billingPlan = entity;
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
