/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { PropertyUnitDetailComponent } from 'app/entities/property-unit/property-unit-detail.component';
import { PropertyUnit } from 'app/shared/model/property-unit.model';

describe('Component Tests', () => {
    describe('PropertyUnit Management Detail Component', () => {
        let comp: PropertyUnitDetailComponent;
        let fixture: ComponentFixture<PropertyUnitDetailComponent>;
        const route = ({ data: of({ propertyUnit: new PropertyUnit(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [PropertyUnitDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PropertyUnitDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PropertyUnitDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.propertyUnit).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
