import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SenErhTestModule } from '../../../test.module';
import { CongeDetailComponent } from 'app/entities/conge/conge-detail.component';
import { Conge } from 'app/shared/model/conge.model';

describe('Component Tests', () => {
  describe('Conge Management Detail Component', () => {
    let comp: CongeDetailComponent;
    let fixture: ComponentFixture<CongeDetailComponent>;
    const route = ({ data: of({ conge: new Conge(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [CongeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CongeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CongeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load conge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.conge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
