/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { ApplianceDetailComponent } from 'app/entities/appliance/appliance-detail.component';
import { Appliance } from 'app/shared/model/appliance.model';

describe('Component Tests', () => {
    describe('Appliance Management Detail Component', () => {
        let comp: ApplianceDetailComponent;
        let fixture: ComponentFixture<ApplianceDetailComponent>;
        const route = ({ data: of({ appliance: new Appliance(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [ApplianceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApplianceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApplianceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.appliance).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
