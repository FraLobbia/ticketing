import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  Ticket,
  TicketStatusEnum,
} from '../../../../shared/models/ticket.model';
import { AuthService } from '../../../../core/services/auth.service';
import { TicketService } from '../../services/ticket.service';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-ticket-create',
  templateUrl: './create-ticket.component.html',
  styleUrls: ['./create-ticket.component.scss'],
})
export class CreateTicketComponent implements OnInit, OnDestroy {
  /**
   * Variabili
   */
  ticketForm!: FormGroup;
  statuses = Object.values(TicketStatusEnum); // Enum values for the dropdown

  /**
   * Costruttore
   */
  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private ticketService: TicketService,
    private router: Router
  ) { }

  /**
   * Inizializza il componente
   * Crea il form per la creazione di un nuovo ticket
   */
  ngOnInit(): void {
    this.initializeForm();
  }

  /**
   * Inizializza il form per la creazione di un nuovo ticket
   */
  initializeForm(): void {
    this.ticketForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  /**
   * Invia il form al backend per creare un nuovo ticket
   */
  onSubmit(): void {
    if (this.ticketForm.valid) {
      const newTicket: Ticket = {
        ...this.ticketForm.value,
        author: {
          id: this.authService.getTokenPayload()?.idAccount,
        },
      };
      this.ticketService.create(newTicket).subscribe((ticket: Ticket) => {
        console.groupCollapsed('Ticket creato con successo (...)');
        console.table(ticket);
        console.groupEnd();
        this.router.navigate(['/tickets/' + ticket.id]);
      });
    }
  }

  /**
   * Destroy del componente e delle sottoscrizioni
   */
  destroy$ = new Subject<void>();
  ngOnDestroy(): void {
    this.destroy$.next(); // Emissione del segnale per interrompere le sottoscrizioni
    this.destroy$.complete();
  }
}
