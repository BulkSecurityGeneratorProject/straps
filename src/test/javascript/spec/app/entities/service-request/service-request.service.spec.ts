/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ServiceRequestService } from 'app/entities/service-request/service-request.service';
import { IServiceRequest, ServiceRequest } from 'app/shared/model/service-request.model';

describe('Service Tests', () => {
    describe('ServiceRequest Service', () => {
        let injector: TestBed;
        let service: ServiceRequestService;
        let httpMock: HttpTestingController;
        let elemDefault: IServiceRequest;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ServiceRequestService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ServiceRequest(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, currentDate, 'AAAAAAA', 0, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        requestDateTime: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a ServiceRequest', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        requestDateTime: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        requestDateTime: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ServiceRequest(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ServiceRequest', async () => {
                const returnedFromService = Object.assign(
                    {
                        companyId: 1,
                        propertyUnitsId: 1,
                        propertiesId: 1,
                        propertyVendorsId: 1,
                        category: 1,
                        subCategory: 1,
                        urgency: 1,
                        unitId: 1,
                        propertyId: 1,
                        requestDateTime: currentDate.format(DATE_TIME_FORMAT),
                        description: 'BBBBBB',
                        assignmentStatus: 1,
                        version: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        requestDateTime: currentDate
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

            it('should return a list of ServiceRequest', async () => {
                const returnedFromService = Object.assign(
                    {
                        companyId: 1,
                        propertyUnitsId: 1,
                        propertiesId: 1,
                        propertyVendorsId: 1,
                        category: 1,
                        subCategory: 1,
                        urgency: 1,
                        unitId: 1,
                        propertyId: 1,
                        requestDateTime: currentDate.format(DATE_TIME_FORMAT),
                        description: 'BBBBBB',
                        assignmentStatus: 1,
                        version: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        requestDateTime: currentDate
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

            it('should delete a ServiceRequest', async () => {
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
