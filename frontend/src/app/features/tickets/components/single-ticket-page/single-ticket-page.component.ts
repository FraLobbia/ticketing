import { Component } from '@angular/core';
import { Ticket, TicketStatus } from '../../../../shared/models/ticket.model';
import { TicketService } from '../../services/ticket.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-single-ticket-page',
  templateUrl: './single-ticket-page.component.html',
  styleUrl: './single-ticket-page.component.scss',
})
export class SingleTicketPageComponent {
  // Variabili
  ticket: Ticket | undefined;
  ticketStatus = TicketStatus;
  ticketStatusKeys: string[] = []; // Array per i valori enum
  id: number | undefined;
  ticketForm: FormGroup;

  constructor(
    private ticketService: TicketService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.ticketForm = this.fb.group({
      status: this.ticket?.status,
    });
  }

  onSubmit(): void {
    console.log('Form Submitted', this.ticketForm.value);
    // Qui puoi chiamare il servizio per inviare il form al backend
  }

  ngOnInit(): void {
    this.ticketStatusKeys = Object.keys(this.ticketStatus);
    this.route.params.subscribe((params) => {
      this.id = +params['id'];
      this.ticketService.getTicketById(this.id).subscribe((data: Ticket) => {
        console.log('Ticket:', data);
        this.ticket = data;
      });
    });
  }

  getStatusClass(status: any): string {
    switch (status) {
      case TicketStatus.OPEN:
        return 'status-open';
      case TicketStatus.IN_PROGRESS:
        return 'status-in-progress';
      case TicketStatus.CLOSED:
        return 'status-closed';
      default:
        return '';
    }
  }
}
