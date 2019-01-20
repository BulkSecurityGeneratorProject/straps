/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { LookupTypeDetailComponent } from 'app/entities/lookup-type/lookup-type-detail.component';
import { LookupType } from 'app/shared/model/lookup-type.model';

describe('Component Tests', () => {
    describe('LookupType Management Detail Component', () => {
        let comp: LookupTypeDetailComponent;
        let fixture: ComponentFixture<LookupTypeDetailComponent>;
        const route = ({ data: of({ lookupType: new LookupType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [LookupTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LookupTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LookupTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lookupType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
