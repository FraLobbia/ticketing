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
  ticketStatus = TicketStatus;
  ticketStatusKeys: TicketStatus[] = []; // Array per i valori enum
  id: number | undefined;
  ticketForm: FormGroup;
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
    this.ticketForm = this.fb.group({
      status: '',
    });
  }
  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  ngOnInit(): void {
    this.ticketStatusKeys = Object.keys(this.ticketStatus) as TicketStatus[];

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

  updateForm(): void {
    if (this.ticket) {
      this.ticketForm.patchValue({
        status: this.ticket.status,
      });
    }
  }

  onChangeStatus(): void {
    if (this.ticketForm.valid) {
      console.log(this.ticketForm.value.status);

      this.ticketService
        .updateTicketStatus(this.id!, this.ticketForm.value.status)
        .subscribe((data: Ticket) => {
          this.ticket = data;
          this.ticketService
            .getViewingTicketsFromDb()
            .subscribe((tickets) => {});
        });
    }
  }
}
