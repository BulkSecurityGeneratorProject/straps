/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { LookupTypeService } from 'app/entities/lookup-type/lookup-type.service';
import { ILookupType, LookupType } from 'app/shared/model/lookup-type.model';

describe('Service Tests', () => {
    describe('LookupType Service', () => {
        let injector: TestBed;
        let service: LookupTypeService;
        let httpMock: HttpTestingController;
        let elemDefault: ILookupType;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(LookupTypeService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new LookupType(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a LookupType', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new LookupType(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a LookupType', async () => {
                const returnedFromService = Object.assign(
                    {
                        companyId: 1,
                        lookupTypeCode: 'BBBBBB',
                        lookupTypeValue: 'BBBBBB',
                        flex1Descr: 'BBBBBB',
                        flex2Descr: 'BBBBBB',
                        flex3Descr: 'BBBBBB',
                        flex4Descr: 'BBBBBB',
                        version: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of LookupType', async () => {
                const returnedFromService = Object.assign(
                    {
                        companyId: 1,
                        lookupTypeCode: 'BBBBBB',
                        lookupTypeValue: 'BBBBBB',
                        flex1Descr: 'BBBBBB',
                        flex2Descr: 'BBBBBB',
                        flex3Descr: 'BBBBBB',
                        flex4Descr: 'BBBBBB',
                        version: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a LookupType', async () => {
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
