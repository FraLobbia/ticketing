import { Component, EventEmitter, OnDestroy, Output } from '@angular/core';
import { Ticket, TicketStatus } from '../../../../shared/models/ticket.model';
import { TicketService } from '../../services/ticket.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-single-ticket-page',
  templateUrl: './single-ticket-page.component.html',
  styleUrl: './single-ticket-page.component.scss',
})
export class SingleTicketPageComponent implements OnDestroy {
  /**
   * Variabili
   */
  ticket!: Ticket;
  ticketStatusKeys: TicketStatus[] = []; // Array per i valori enum
  id: number | undefined;
  statusForm: FormGroup; // Select form per cambiare lo stato del ticket
  private subscriptions: Subscription[] = [];
  @Output() statusChanged = new EventEmitter<Ticket>();

  /**
   * Costruttore
   */
  constructor(
    private ticketService: TicketService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.statusForm = this.fb.group({
      status: '',
    });
  }
  /**
   * Metodo per distruggere le sottoscrizioni al destroy del componente
   */
  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  /**
   * Inizializza il componente
   */
  ngOnInit(): void {
    this.ticketStatusKeys = Object.keys(TicketStatus) as TicketStatus[];
    const getIdFromRouteSub = this.route.params.subscribe((params) => {
      this.id = +params['id'];
      const getTicketSub = this.ticketService
        .getTicketById(this.id)
        .subscribe((data: Ticket) => {
          this.ticket = data;
          this.updateForm();
        });
      this.subscriptions.push(getTicketSub);
    });
    this.subscriptions.push(getIdFromRouteSub);
  }

  /**
   * Aggiorna il form con lo stato attuale del ticket.
   */
  updateForm(): void {
    if (this.ticket) {
      this.statusForm.patchValue({
        status: this.ticket.status,
      });
    }
  }

  /**
   * Evento per cambiare lo stato del ticket.
   * Si iscrive anche al servizio per aggiornare la lista dei ticket visualizzati.
   */
  onChangeStatus(): void {
    this.ticketService
      .updateTicketStatus(this.id!, this.statusForm.value.status)
      .subscribe((data: Ticket) => {
        this.ticket = data;
        this.ticketService.getViewingTicketsFromDb().subscribe((tickets) => {});
      });
  }
}
