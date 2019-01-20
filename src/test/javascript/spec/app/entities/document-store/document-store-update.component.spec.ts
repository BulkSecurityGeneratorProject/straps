/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { DocumentStoreUpdateComponent } from 'app/entities/document-store/document-store-update.component';
import { DocumentStoreService } from 'app/entities/document-store/document-store.service';
import { DocumentStore } from 'app/shared/model/document-store.model';

describe('Component Tests', () => {
    describe('DocumentStore Management Update Component', () => {
        let comp: DocumentStoreUpdateComponent;
        let fixture: ComponentFixture<DocumentStoreUpdateComponent>;
        let service: DocumentStoreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [DocumentStoreUpdateComponent]
            })
                .overrideTemplate(DocumentStoreUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DocumentStoreUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentStoreService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DocumentStore(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.documentStore = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DocumentStore();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.documentStore = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
