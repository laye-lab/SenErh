import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SenErhTestModule } from '../../../test.module';
import { CongeUpdateComponent } from 'app/entities/conge/conge-update.component';
import { CongeService } from 'app/entities/conge/conge.service';
import { Conge } from 'app/shared/model/conge.model';

describe('Component Tests', () => {
  describe('Conge Management Update Component', () => {
    let comp: CongeUpdateComponent;
    let fixture: ComponentFixture<CongeUpdateComponent>;
    let service: CongeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [CongeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CongeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CongeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CongeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Conge(123);
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
        const entity = new Conge();
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
