/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { IncomeProjectionDetailComponent } from 'app/entities/income-projection/income-projection-detail.component';
import { IncomeProjection } from 'app/shared/model/income-projection.model';

describe('Component Tests', () => {
    describe('IncomeProjection Management Detail Component', () => {
        let comp: IncomeProjectionDetailComponent;
        let fixture: ComponentFixture<IncomeProjectionDetailComponent>;
        const route = ({ data: of({ incomeProjection: new IncomeProjection(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [IncomeProjectionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IncomeProjectionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IncomeProjectionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.incomeProjection).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
