import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SenErhTestModule } from '../../../test.module';
import { HistoriqueCongeUpdateComponent } from 'app/entities/historique-conge/historique-conge-update.component';
import { HistoriqueCongeService } from 'app/entities/historique-conge/historique-conge.service';
import { HistoriqueConge } from 'app/shared/model/historique-conge.model';

describe('Component Tests', () => {
  describe('HistoriqueConge Management Update Component', () => {
    let comp: HistoriqueCongeUpdateComponent;
    let fixture: ComponentFixture<HistoriqueCongeUpdateComponent>;
    let service: HistoriqueCongeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [HistoriqueCongeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(HistoriqueCongeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistoriqueCongeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoriqueCongeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HistoriqueConge(123);
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
        const entity = new HistoriqueConge();
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
