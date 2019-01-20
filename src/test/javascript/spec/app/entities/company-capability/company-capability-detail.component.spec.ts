/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { CompanyCapabilityDetailComponent } from 'app/entities/company-capability/company-capability-detail.component';
import { CompanyCapability } from 'app/shared/model/company-capability.model';

describe('Component Tests', () => {
    describe('CompanyCapability Management Detail Component', () => {
        let comp: CompanyCapabilityDetailComponent;
        let fixture: ComponentFixture<CompanyCapabilityDetailComponent>;
        const route = ({ data: of({ companyCapability: new CompanyCapability(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [CompanyCapabilityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CompanyCapabilityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompanyCapabilityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.companyCapability).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
