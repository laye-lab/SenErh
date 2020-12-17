import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenErhTestModule } from '../../../test.module';
import { ValidationStepComponent } from 'app/entities/validation-step/validation-step.component';
import { ValidationStepService } from 'app/entities/validation-step/validation-step.service';
import { ValidationStep } from 'app/shared/model/validation-step.model';

describe('Component Tests', () => {
  describe('ValidationStep Management Component', () => {
    let comp: ValidationStepComponent;
    let fixture: ComponentFixture<ValidationStepComponent>;
    let service: ValidationStepService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [ValidationStepComponent],
      })
        .overrideTemplate(ValidationStepComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ValidationStepComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ValidationStepService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ValidationStep(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.validationSteps && comp.validationSteps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
