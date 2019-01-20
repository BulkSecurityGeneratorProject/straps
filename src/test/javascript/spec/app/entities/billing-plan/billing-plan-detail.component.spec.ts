/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { BillingPlanDetailComponent } from 'app/entities/billing-plan/billing-plan-detail.component';
import { BillingPlan } from 'app/shared/model/billing-plan.model';

describe('Component Tests', () => {
    describe('BillingPlan Management Detail Component', () => {
        let comp: BillingPlanDetailComponent;
        let fixture: ComponentFixture<BillingPlanDetailComponent>;
        const route = ({ data: of({ billingPlan: new BillingPlan(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [BillingPlanDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BillingPlanDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BillingPlanDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.billingPlan).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
