/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { RentRollDetailComponent } from 'app/entities/rent-roll/rent-roll-detail.component';
import { RentRoll } from 'app/shared/model/rent-roll.model';

describe('Component Tests', () => {
    describe('RentRoll Management Detail Component', () => {
        let comp: RentRollDetailComponent;
        let fixture: ComponentFixture<RentRollDetailComponent>;
        const route = ({ data: of({ rentRoll: new RentRoll(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [RentRollDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RentRollDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RentRollDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rentRoll).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
