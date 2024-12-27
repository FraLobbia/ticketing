import { Component, EventEmitter, OnDestroy, Output } from '@angular/core';
import {
  Ticket,
  TicketStatusEnum,
} from '../../../../shared/models/ticket.model';
import { TicketService } from '../../services/ticket.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { getTicketStatusEnumValue } from '../../../../shared/utility/string-editor.utility';

@Component({
  selector: 'app-single-ticket-page',
  templateUrl: './single-ticket-page.component.html',
  styleUrl: './single-ticket-page.component.scss',
})
export class SingleTicketPageComponent implements OnDestroy {
  /**
   * Variabili
   */
  ticket: Ticket | undefined;
  ticketStatusKeys: string[] = []; // Array per i valori enum
  id: number | undefined;
  statusForm: FormGroup = new FormGroup({});
  getTicketStatusEnumValue = getTicketStatusEnumValue;
  @Output() statusChanged = new EventEmitter<Ticket>();

  /**
   * Costruttore
   */
  constructor(
    private ticketService: TicketService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  /**
   * Inizializza il componente
   */
  ngOnInit(): void {
    this.route.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.id = +params['id'];
      this.ticketStatusKeys = Object.keys(TicketStatusEnum);
      this.initializeForm();
      this.fetchTicketData();
    });
  }

  /**
   * Ottiene i dati del ticket
   */
  fetchTicketData(): void {
    this.ticketService
      .getTicketById(this.id!)
      .pipe(takeUntil(this.destroy$))
      .subscribe((data: Ticket) => {
        this.ticket = data;
        this.updateForm();
      });
  }

  /**
   * Inizializza il form per lo stato del ticket
   */
  initializeForm(): void {
    this.statusForm = this.fb.group({
      status: '',
    });
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
  handleStatusChange(): void {
    this.ticketService
      .updateTicketStatus(this.id!, this.statusForm.value.status)
      .subscribe((data: Ticket) => {
        this.ticket = data;
        this.ticketService.getViewingTicketsFromDB().subscribe((tickets) => {});
      });
  }

  /**
   * Destroy del componente e delle sottoscrizioni
   */
  destroy$ = new Subject<void>();
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
