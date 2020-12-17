import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenErhTestModule } from '../../../test.module';
import { AgentsComponent } from 'app/entities/agents/agents.component';
import { AgentsService } from 'app/entities/agents/agents.service';
import { Agents } from 'app/shared/model/agents.model';

describe('Component Tests', () => {
  describe('Agents Management Component', () => {
    let comp: AgentsComponent;
    let fixture: ComponentFixture<AgentsComponent>;
    let service: AgentsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [AgentsComponent],
      })
        .overrideTemplate(AgentsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgentsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgentsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Agents(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.agents && comp.agents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
