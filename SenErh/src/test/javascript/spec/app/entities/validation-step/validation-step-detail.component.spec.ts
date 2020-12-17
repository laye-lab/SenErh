import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SenErhTestModule } from '../../../test.module';
import { ValidationStepDetailComponent } from 'app/entities/validation-step/validation-step-detail.component';
import { ValidationStep } from 'app/shared/model/validation-step.model';

describe('Component Tests', () => {
  describe('ValidationStep Management Detail Component', () => {
    let comp: ValidationStepDetailComponent;
    let fixture: ComponentFixture<ValidationStepDetailComponent>;
    const route = ({ data: of({ validationStep: new ValidationStep(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [ValidationStepDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ValidationStepDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ValidationStepDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load validationStep on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.validationStep).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
