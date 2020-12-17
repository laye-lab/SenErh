import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SenErhTestModule } from '../../../test.module';
import { AgentsUpdateComponent } from 'app/entities/agents/agents-update.component';
import { AgentsService } from 'app/entities/agents/agents.service';
import { Agents } from 'app/shared/model/agents.model';

describe('Component Tests', () => {
  describe('Agents Management Update Component', () => {
    let comp: AgentsUpdateComponent;
    let fixture: ComponentFixture<AgentsUpdateComponent>;
    let service: AgentsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [AgentsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AgentsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgentsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgentsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Agents(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Agents();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
