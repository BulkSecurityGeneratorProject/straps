/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { DocumentStoreDetailComponent } from 'app/entities/document-store/document-store-detail.component';
import { DocumentStore } from 'app/shared/model/document-store.model';

describe('Component Tests', () => {
    describe('DocumentStore Management Detail Component', () => {
        let comp: DocumentStoreDetailComponent;
        let fixture: ComponentFixture<DocumentStoreDetailComponent>;
        const route = ({ data: of({ documentStore: new DocumentStore(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [DocumentStoreDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DocumentStoreDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentStoreDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.documentStore).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
