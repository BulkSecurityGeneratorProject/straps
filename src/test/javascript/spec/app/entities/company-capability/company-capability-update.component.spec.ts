/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { CompanyCapabilityUpdateComponent } from 'app/entities/company-capability/company-capability-update.component';
import { CompanyCapabilityService } from 'app/entities/company-capability/company-capability.service';
import { CompanyCapability } from 'app/shared/model/company-capability.model';

describe('Component Tests', () => {
    describe('CompanyCapability Management Update Component', () => {
        let comp: CompanyCapabilityUpdateComponent;
        let fixture: ComponentFixture<CompanyCapabilityUpdateComponent>;
        let service: CompanyCapabilityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [CompanyCapabilityUpdateComponent]
            })
                .overrideTemplate(CompanyCapabilityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CompanyCapabilityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyCapabilityService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CompanyCapability(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.companyCapability = entity;
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
                    const entity = new CompanyCapability();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.companyCapability = entity;
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
