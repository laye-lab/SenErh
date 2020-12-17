import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenErhTestModule } from '../../../test.module';
import { CongeComponent } from 'app/entities/conge/conge.component';
import { CongeService } from 'app/entities/conge/conge.service';
import { Conge } from 'app/shared/model/conge.model';

describe('Component Tests', () => {
  describe('Conge Management Component', () => {
    let comp: CongeComponent;
    let fixture: ComponentFixture<CongeComponent>;
    let service: CongeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [CongeComponent],
      })
        .overrideTemplate(CongeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CongeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CongeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Conge(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.conges && comp.conges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
