import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SenErhTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { HistoriqueCongeDeleteDialogComponent } from 'app/entities/historique-conge/historique-conge-delete-dialog.component';
import { HistoriqueCongeService } from 'app/entities/historique-conge/historique-conge.service';

describe('Component Tests', () => {
  describe('HistoriqueConge Management Delete Component', () => {
    let comp: HistoriqueCongeDeleteDialogComponent;
    let fixture: ComponentFixture<HistoriqueCongeDeleteDialogComponent>;
    let service: HistoriqueCongeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SenErhTestModule],
        declarations: [HistoriqueCongeDeleteDialogComponent],
      })
        .overrideTemplate(HistoriqueCongeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistoriqueCongeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistoriqueCongeService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
