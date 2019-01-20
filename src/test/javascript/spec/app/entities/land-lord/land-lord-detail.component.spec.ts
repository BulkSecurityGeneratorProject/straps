/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { LandLordDetailComponent } from 'app/entities/land-lord/land-lord-detail.component';
import { LandLord } from 'app/shared/model/land-lord.model';

describe('Component Tests', () => {
    describe('LandLord Management Detail Component', () => {
        let comp: LandLordDetailComponent;
        let fixture: ComponentFixture<LandLordDetailComponent>;
        const route = ({ data: of({ landLord: new LandLord(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [LandLordDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LandLordDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LandLordDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.landLord).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
