/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { PromalyV5TestModule } from '../../../test.module';
import { MortgageUpdateComponent } from 'app/entities/mortgage/mortgage-update.component';
import { MortgageService } from 'app/entities/mortgage/mortgage.service';
import { Mortgage } from 'app/shared/model/mortgage.model';

describe('Component Tests', () => {
    describe('Mortgage Management Update Component', () => {
        let comp: MortgageUpdateComponent;
        let fixture: ComponentFixture<MortgageUpdateComponent>;
        let service: MortgageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PromalyV5TestModule],
                declarations: [MortgageUpdateComponent]
            })
                .overrideTemplate(MortgageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MortgageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MortgageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Mortgage(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mortgage = entity;
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
                    const entity = new Mortgage();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mortgage = entity;
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
