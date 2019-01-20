/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PropertyUnitService } from 'app/entities/property-unit/property-unit.service';
import { IPropertyUnit, PropertyUnit } from 'app/shared/model/property-unit.model';

describe('Service Tests', () => {
    describe('PropertyUnit Service', () => {
        let injector: TestBed;
        let service: PropertyUnitService;
        let httpMock: HttpTestingController;
        let elemDefault: IPropertyUnit;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PropertyUnitService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new PropertyUnit(
                0,
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                0,
                false,
                0,
                0,
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                false,
                0,
                false,
                0,
                false,
                0,
                currentDate,
                0,
                0
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        lastRenovated: currentDate.format(DATE_FORMAT)
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

            it('should create a PropertyUnit', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        lastRenovated: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        lastRenovated: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new PropertyUnit(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a PropertyUnit', async () => {
                const returnedFromService = Object.assign(
                    {
                        unitNo: 'BBBBBB',
                        description: 'BBBBBB',
                        floors: 1,
                        netArea: 1,
                        grossArea: 1,
                        residential: true,
                        totalRooms: 1,
                        noOfBrs: 1,
                        noOfFb: 1,
                        noOfHb: 1,
                        propertyMailboxNum: 'BBBBBB',
                        propertyParkingLotNum: 'BBBBBB',
                        gasHeating: true,
                        whoPaysHeating: 1,
                        electric: true,
                        whoPaysElectric: 1,
                        water: true,
                        whoPaysWater: 1,
                        lastRenovated: currentDate.format(DATE_FORMAT),
                        currentRentPerSqft: 1,
                        version: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        lastRenovated: currentDate
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

            it('should return a list of PropertyUnit', async () => {
                const returnedFromService = Object.assign(
                    {
                        unitNo: 'BBBBBB',
                        description: 'BBBBBB',
                        floors: 1,
                        netArea: 1,
                        grossArea: 1,
                        residential: true,
                        totalRooms: 1,
                        noOfBrs: 1,
                        noOfFb: 1,
                        noOfHb: 1,
                        propertyMailboxNum: 'BBBBBB',
                        propertyParkingLotNum: 'BBBBBB',
                        gasHeating: true,
                        whoPaysHeating: 1,
                        electric: true,
                        whoPaysElectric: 1,
                        water: true,
                        whoPaysWater: 1,
                        lastRenovated: currentDate.format(DATE_FORMAT),
                        currentRentPerSqft: 1,
                        version: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        lastRenovated: currentDate
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

            it('should delete a PropertyUnit', async () => {
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
