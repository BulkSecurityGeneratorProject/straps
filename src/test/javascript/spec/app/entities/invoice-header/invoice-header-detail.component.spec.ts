/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { InvoiceHeaderDetailComponent } from 'app/entities/invoice-header/invoice-header-detail.component';
import { InvoiceHeader } from 'app/shared/model/invoice-header.model';

describe('Component Tests', () => {
    describe('InvoiceHeader Management Detail Component', () => {
        let comp: InvoiceHeaderDetailComponent;
        let fixture: ComponentFixture<InvoiceHeaderDetailComponent>;
        const route = ({ data: of({ invoiceHeader: new InvoiceHeader(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [InvoiceHeaderDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InvoiceHeaderDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InvoiceHeaderDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.invoiceHeader).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
