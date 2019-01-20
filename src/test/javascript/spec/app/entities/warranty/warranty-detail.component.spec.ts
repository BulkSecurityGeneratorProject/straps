/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { WarrantyDetailComponent } from 'app/entities/warranty/warranty-detail.component';
import { Warranty } from 'app/shared/model/warranty.model';

describe('Component Tests', () => {
    describe('Warranty Management Detail Component', () => {
        let comp: WarrantyDetailComponent;
        let fixture: ComponentFixture<WarrantyDetailComponent>;
        const route = ({ data: of({ warranty: new Warranty(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [WarrantyDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WarrantyDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WarrantyDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.warranty).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
