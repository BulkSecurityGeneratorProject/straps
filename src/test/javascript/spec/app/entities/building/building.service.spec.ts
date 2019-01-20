/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BuildingService } from 'app/entities/building/building.service';
import { IBuilding, Building } from 'app/shared/model/building.model';

describe('Service Tests', () => {
    describe('Building Service', () => {
        let injector: TestBed;
        let service: BuildingService;
        let httpMock: HttpTestingController;
        let elemDefault: IBuilding;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(BuildingService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Building(0, 'AAAAAAA', currentDate, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        yearBuilt: currentDate.format(DATE_FORMAT)
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

            it('should create a Building', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        yearBuilt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        yearBuilt: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Building(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Building', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        yearBuilt: currentDate.format(DATE_FORMAT),
                        noOfloors: 1,
                        buildingSize: 1,
                        coveredArea: 1,
                        landArea: 1,
                        noOfRentalUnit: 1,
                        parkingSpaces: 1,
                        totalSpaceAvaliable: 1,
                        totalUnitLevel: 1,
                        currentRentPerSqft: 1,
                        description: 'BBBBBB',
                        version: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        yearBuilt: currentDate
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

            it('should return a list of Building', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        yearBuilt: currentDate.format(DATE_FORMAT),
                        noOfloors: 1,
                        buildingSize: 1,
                        coveredArea: 1,
                        landArea: 1,
                        noOfRentalUnit: 1,
                        parkingSpaces: 1,
                        totalSpaceAvaliable: 1,
                        totalUnitLevel: 1,
                        currentRentPerSqft: 1,
                        description: 'BBBBBB',
                        version: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        yearBuilt: currentDate
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

            it('should delete a Building', async () => {
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