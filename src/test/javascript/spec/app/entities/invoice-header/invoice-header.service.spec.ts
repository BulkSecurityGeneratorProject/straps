/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InvoiceHeaderService } from 'app/entities/invoice-header/invoice-header.service';
import { IInvoiceHeader, InvoiceHeader } from 'app/shared/model/invoice-header.model';

describe('Service Tests', () => {
    describe('InvoiceHeader Service', () => {
        let injector: TestBed;
        let service: InvoiceHeaderService;
        let httpMock: HttpTestingController;
        let elemDefault: IInvoiceHeader;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(InvoiceHeaderService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new InvoiceHeader(
                0,
                0,
                0,
                0,
                currentDate,
                0,
                currentDate,
                currentDate,
                0,
                0,
                0,
                0,
                0,
                0,
                currentDate,
                0,
                'AAAAAAA',
                'AAAAAAA',
                false,
                0,
                false,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        paymentDate: currentDate.format(DATE_FORMAT)
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

            it('should create a InvoiceHeader', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        paymentDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        invoiceDate: currentDate,
                        fromDate: currentDate,
                        toDate: currentDate,
                        paymentDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new InvoiceHeader(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a InvoiceHeader', async () => {
                const returnedFromService = Object.assign(
                    {
                        planId: 1,
                        serviceproviderId: 1,
                        invoiceNum: 1,
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        invoiceStatus: 1,
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        amount: 1,
                        fees: 1,
                        currency: 1,
                        paymentTerms: 1,
                        paymentStatus: 1,
                        paymentMethod: 1,
                        paymentDate: currentDate.format(DATE_FORMAT),
                        paymentAmount: 1,
                        paymentRef: 'BBBBBB',
                        comments: 'BBBBBB',
                        adhoc: true,
                        billToCompany: 1,
                        legacy: true,
                        payor: 'BBBBBB',
                        paymentComments: 'BBBBBB',
                        emailDate: 'BBBBBB',
                        emailAddress: 'BBBBBB',
                        keyHash: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        invoiceDate: currentDate,
                        fromDate: currentDate,
                        toDate: currentDate,
                        paymentDate: currentDate
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

            it('should return a list of InvoiceHeader', async () => {
                const returnedFromService = Object.assign(
                    {
                        planId: 1,
                        serviceproviderId: 1,
                        invoiceNum: 1,
                        invoiceDate: currentDate.format(DATE_FORMAT),
                        invoiceStatus: 1,
                        fromDate: currentDate.format(DATE_FORMAT),
                        toDate: currentDate.format(DATE_FORMAT),
                        amount: 1,
                        fees: 1,
                        currency: 1,
                        paymentTerms: 1,
                        paymentStatus: 1,
                        paymentMethod: 1,
                        paymentDate: currentDate.format(DATE_FORMAT),
                        paymentAmount: 1,
                        paymentRef: 'BBBBBB',
                        comments: 'BBBBBB',
                        adhoc: true,
                        billToCompany: 1,
                        legacy: true,
                        payor: 'BBBBBB',
                        paymentComments: 'BBBBBB',
                        emailDate: 'BBBBBB',
                        emailAddress: 'BBBBBB',
                        keyHash: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        invoiceDate: currentDate,
                        fromDate: currentDate,
                        toDate: currentDate,
                        paymentDate: currentDate
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

            it('should delete a InvoiceHeader', async () => {
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
