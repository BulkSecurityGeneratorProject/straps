/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { LeaseService } from 'app/entities/lease/lease.service';
import { ILease, Lease } from 'app/shared/model/lease.model';

describe('Service Tests', () => {
    describe('Lease Service', () => {
        let injector: TestBed;
        let service: LeaseService;
        let httpMock: HttpTestingController;
        let elemDefault: ILease;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(LeaseService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Lease(
                0,
                currentDate,
                0,
                0,
                0,
                0,
                0,
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
                0,
                false,
                0,
                'AAAAAAA',
                false,
                false,
                false,
                'AAAAAAA',
                0,
                0,
                0,
                0,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        leaseSignedDate: currentDate.format(DATE_FORMAT),
                        leaseStartDate: currentDate.format(DATE_FORMAT),
                        leaseEndDate: currentDate.format(DATE_FORMAT),
                        proRataEndDate: currentDate.format(DATE_FORMAT)
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

            it('should create a Lease', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        leaseSignedDate: currentDate.format(DATE_FORMAT),
                        leaseStartDate: currentDate.format(DATE_FORMAT),
                        leaseEndDate: currentDate.format(DATE_FORMAT),
                        proRataEndDate: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        leaseSignedDate: currentDate,
                        leaseStartDate: currentDate,
                        leaseEndDate: currentDate,
                        proRataEndDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Lease(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Lease', async () => {
                const returnedFromService = Object.assign(
                    {
                        leaseSignedDate: currentDate.format(DATE_FORMAT),
                        landlordsId: 1,
                        landlordAgent: 1,
                        tenantsId: 1,
                        addressId: 1,
                        numOccupants: 1,
                        leaseTerm: 1,
                        leaseStartDate: currentDate.format(DATE_FORMAT),
                        leaseEndDate: currentDate.format(DATE_FORMAT),
                        rentAmount: 1,
                        rentPeriod: 1,
                        totalTermRent: 1,
                        rentEscalationPerc: 1,
                        proRataStartDate: 1,
                        proRataRentAmount: 1,
                        proRataEndDate: currentDate.format(DATE_FORMAT),
                        additionalCharges: 1,
                        securityDeposit: 1,
                        petsAllowed: true,
                        petType: 1,
                        petDescription: 'BBBBBB',
                        water: true,
                        gas: true,
                        electric: true,
                        otherUtilities: 'BBBBBB',
                        terminationNoticePeriod: 1,
                        agencyCompany: 1,
                        managementCompany: 1,
                        propertyId: 1,
                        additionalNotes: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        leaseSignedDate: currentDate,
                        leaseStartDate: currentDate,
                        leaseEndDate: currentDate,
                        proRataEndDate: currentDate
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

            it('should return a list of Lease', async () => {
                const returnedFromService = Object.assign(
                    {
                        leaseSignedDate: currentDate.format(DATE_FORMAT),
                        landlordsId: 1,
                        landlordAgent: 1,
                        tenantsId: 1,
                        addressId: 1,
                        numOccupants: 1,
                        leaseTerm: 1,
                        leaseStartDate: currentDate.format(DATE_FORMAT),
                        leaseEndDate: currentDate.format(DATE_FORMAT),
                        rentAmount: 1,
                        rentPeriod: 1,
                        totalTermRent: 1,
                        rentEscalationPerc: 1,
                        proRataStartDate: 1,
                        proRataRentAmount: 1,
                        proRataEndDate: currentDate.format(DATE_FORMAT),
                        additionalCharges: 1,
                        securityDeposit: 1,
                        petsAllowed: true,
                        petType: 1,
                        petDescription: 'BBBBBB',
                        water: true,
                        gas: true,
                        electric: true,
                        otherUtilities: 'BBBBBB',
                        terminationNoticePeriod: 1,
                        agencyCompany: 1,
                        managementCompany: 1,
                        propertyId: 1,
                        additionalNotes: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        leaseSignedDate: currentDate,
                        leaseStartDate: currentDate,
                        leaseEndDate: currentDate,
                        proRataEndDate: currentDate
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

            it('should delete a Lease', async () => {
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
