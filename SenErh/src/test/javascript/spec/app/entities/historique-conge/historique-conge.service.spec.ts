import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { HistoriqueCongeService } from 'app/entities/historique-conge/historique-conge.service';
import { IHistoriqueConge, HistoriqueConge } from 'app/shared/model/historique-conge.model';

describe('Service Tests', () => {
  describe('HistoriqueConge Service', () => {
    let injector: TestBed;
    let service: HistoriqueCongeService;
    let httpMock: HttpTestingController;
    let elemDefault: IHistoriqueConge;
    let expectedResult: IHistoriqueConge | IHistoriqueConge[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(HistoriqueCongeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new HistoriqueConge(0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateDernierDepart: currentDate.format(DATE_FORMAT),
            dateDernierRetour: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a HistoriqueConge', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateDernierDepart: currentDate.format(DATE_FORMAT),
            dateDernierRetour: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDernierDepart: currentDate,
            dateDernierRetour: currentDate,
          },
          returnedFromService
        );

        service.create(new HistoriqueConge()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HistoriqueConge', () => {
        const returnedFromService = Object.assign(
          {
            dateDernierDepart: currentDate.format(DATE_FORMAT),
            dateDernierRetour: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDernierDepart: currentDate,
            dateDernierRetour: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of HistoriqueConge', () => {
        const returnedFromService = Object.assign(
          {
            dateDernierDepart: currentDate.format(DATE_FORMAT),
            dateDernierRetour: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDernierDepart: currentDate,
            dateDernierRetour: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a HistoriqueConge', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
