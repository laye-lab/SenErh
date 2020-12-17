import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SenErhTestModule } from '../../../test.module';
import { AgentsDetailComponent } from 'app/entities/agents/agents-detail.component';
import { Agents } from 'app/shared/model/agents.model';

describe('Component Tests', () => {
  describe('Agents Management Detail Component', () => {
    let comp: AgentsDetailComponent;
    let fixture: ComponentFixture<AgentsDetailComponent>;
    const route = ({ data: of({ agents: new Agents(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [AgentsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AgentsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgentsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load agents on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agents).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
