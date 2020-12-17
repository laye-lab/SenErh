import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AgentsService } from 'app/entities/agents/agents.service';
import { IAgents, Agents } from 'app/shared/model/agents.model';
import { Statut } from 'app/shared/model/enumerations/statut.model';

describe('Service Tests', () => {
  describe('Agents Service', () => {
    let injector: TestBed;
    let service: AgentsService;
    let httpMock: HttpTestingController;
    let elemDefault: IAgents;
    let expectedResult: IAgents | IAgents[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AgentsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Agents(0, 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', Statut.AMA, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Agents', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Agents()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Agents', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            equipe: 1,
            direction: 'BBBBBB',
            etablissement: 'BBBBBB',
            fonction: 'BBBBBB',
            statut: 'BBBBBB',
            affectation: 'BBBBBB',
            taux: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Agents', () => {
        const returnedFromService = Object.assign(
          {
            nom: 'BBBBBB',
            equipe: 1,
            direction: 'BBBBBB',
            etablissement: 'BBBBBB',
            fonction: 'BBBBBB',
            statut: 'BBBBBB',
            affectation: 'BBBBBB',
            taux: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Agents', () => {
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
