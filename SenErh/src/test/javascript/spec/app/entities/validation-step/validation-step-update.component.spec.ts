import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SenErhTestModule } from '../../../test.module';
import { ValidationStepUpdateComponent } from 'app/entities/validation-step/validation-step-update.component';
import { ValidationStepService } from 'app/entities/validation-step/validation-step.service';
import { ValidationStep } from 'app/shared/model/validation-step.model';

describe('Component Tests', () => {
  describe('ValidationStep Management Update Component', () => {
    let comp: ValidationStepUpdateComponent;
    let fixture: ComponentFixture<ValidationStepUpdateComponent>;
    let service: ValidationStepService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [ValidationStepUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ValidationStepUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ValidationStepUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ValidationStepService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ValidationStep(123);
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
        const entity = new ValidationStep();
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
