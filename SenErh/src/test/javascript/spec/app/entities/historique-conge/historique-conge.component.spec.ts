import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenErhTestModule } from '../../../test.module';
import { HistoriqueCongeComponent } from 'app/entities/historique-conge/historique-conge.component';
import { HistoriqueCongeService } from 'app/entities/historique-conge/historique-conge.service';
import { HistoriqueConge } from 'app/shared/model/historique-conge.model';

describe('Component Tests', () => {
  describe('HistoriqueConge Management Component', () => {
    let comp: HistoriqueCongeComponent;
    let fixture: ComponentFixture<HistoriqueCongeComponent>;
    let service: HistoriqueCongeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [HistoriqueCongeComponent],
      })
        .overrideTemplate(HistoriqueCongeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistoriqueCongeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoriqueCongeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HistoriqueConge(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.historiqueConges && comp.historiqueConges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
