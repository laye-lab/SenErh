import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SenErhTestModule } from '../../../test.module';
import { HistoriqueCongeDetailComponent } from 'app/entities/historique-conge/historique-conge-detail.component';
import { HistoriqueConge } from 'app/shared/model/historique-conge.model';

describe('Component Tests', () => {
  describe('HistoriqueConge Management Detail Component', () => {
    let comp: HistoriqueCongeDetailComponent;
    let fixture: ComponentFixture<HistoriqueCongeDetailComponent>;
    const route = ({ data: of({ historiqueConge: new HistoriqueConge(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [HistoriqueCongeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(HistoriqueCongeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistoriqueCongeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load historiqueConge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.historiqueConge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
