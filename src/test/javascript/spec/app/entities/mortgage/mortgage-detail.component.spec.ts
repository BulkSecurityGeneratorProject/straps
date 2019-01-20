/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { MortgageDetailComponent } from 'app/entities/mortgage/mortgage-detail.component';
import { Mortgage } from 'app/shared/model/mortgage.model';

describe('Component Tests', () => {
    describe('Mortgage Management Detail Component', () => {
        let comp: MortgageDetailComponent;
        let fixture: ComponentFixture<MortgageDetailComponent>;
        const route = ({ data: of({ mortgage: new Mortgage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [MortgageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MortgageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MortgageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mortgage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
