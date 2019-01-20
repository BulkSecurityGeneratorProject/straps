/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BillingPlanService } from 'app/entities/billing-plan/billing-plan.service';
import { IBillingPlan, BillingPlan } from 'app/shared/model/billing-plan.model';

describe('Service Tests', () => {
    describe('BillingPlan Service', () => {
        let injector: TestBed;
        let service: BillingPlanService;
        let httpMock: HttpTestingController;
        let elemDefault: IBillingPlan;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(BillingPlanService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new BillingPlan(
                0,
                'AAAAAAA',
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                0,
                'AAAAAAA',
                0,
                0,
                0,
                0,
                0,
                0,
                'AAAAAAA',
                false,
                'AAAAAAA',
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        effectiveDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a BillingPlan', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        effectiveDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        effectiveDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new BillingPlan(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a BillingPlan', async () => {
                const returnedFromService = Object.assign(
                    {
                        planName: 'BBBBBB',
                        category: 1,
                        memberType: 1,
                        description: 'BBBBBB',
                        prorationDesc: 'BBBBBB',
                        effectiveDate: currentDate.format(DATE_FORMAT),
                        effectiveStatus: 1,
                        association: 'BBBBBB',
                        orderType: 1,
                        accountingBook: 1,
                        rates: 1,
                        currency: 1,
                        basis: 1,
                        initiationFees: 1,
                        coupons: 'BBBBBB',
                        prorated: true,
                        glcode: 'BBBBBB',
                        active: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        effectiveDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of BillingPlan', async () => {
                const returnedFromService = Object.assign(
                    {
                        planName: 'BBBBBB',
                        category: 1,
                        memberType: 1,
                        description: 'BBBBBB',
                        prorationDesc: 'BBBBBB',
                        effectiveDate: currentDate.format(DATE_FORMAT),
                        effectiveStatus: 1,
                        association: 'BBBBBB',
                        orderType: 1,
                        accountingBook: 1,
                        rates: 1,
                        currency: 1,
                        basis: 1,
                        initiationFees: 1,
                        coupons: 'BBBBBB',
                        prorated: true,
                        glcode: 'BBBBBB',
                        active: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        effectiveDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a BillingPlan', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
