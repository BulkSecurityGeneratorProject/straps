/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { DiscountPlanDetailComponent } from 'app/entities/discount-plan/discount-plan-detail.component';
import { DiscountPlan } from 'app/shared/model/discount-plan.model';

describe('Component Tests', () => {
    describe('DiscountPlan Management Detail Component', () => {
        let comp: DiscountPlanDetailComponent;
        let fixture: ComponentFixture<DiscountPlanDetailComponent>;
        const route = ({ data: of({ discountPlan: new DiscountPlan(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [DiscountPlanDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DiscountPlanDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DiscountPlanDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.discountPlan).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
