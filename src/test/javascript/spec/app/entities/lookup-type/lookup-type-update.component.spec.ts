/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { LookupTypeUpdateComponent } from 'app/entities/lookup-type/lookup-type-update.component';
import { LookupTypeService } from 'app/entities/lookup-type/lookup-type.service';
import { LookupType } from 'app/shared/model/lookup-type.model';

describe('Component Tests', () => {
    describe('LookupType Management Update Component', () => {
        let comp: LookupTypeUpdateComponent;
        let fixture: ComponentFixture<LookupTypeUpdateComponent>;
        let service: LookupTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [LookupTypeUpdateComponent]
            })
                .overrideTemplate(LookupTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LookupTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LookupTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LookupType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lookupType = entity;
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
                    const entity = new LookupType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lookupType = entity;
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
