import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ticket, TicketStatus } from '../../../../shared/models/ticket.model';

@Component({
  selector: 'app-ticket-create',
  templateUrl: './create-ticket.component.html',
  styleUrls: ['./create-ticket.component.scss'],
})
export class CreateTicketComponent implements OnInit {
  ticketForm!: FormGroup;
  statuses = Object.values(TicketStatus); // Enum values for the dropdown

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.ticketForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      status: [TicketStatus.OPEN, Validators.required],
      account: this.fb.group({
        id: [null, Validators.required],
        username: ['', Validators.required], // O altre proprietà necessarie
      }),
      createdAt: [new Date(), Validators.required],
      updatedAt: [new Date(), Validators.required],
    });
  }

  onSubmit(): void {
    if (this.ticketForm.valid) {
      const newTicket: Ticket = this.ticketForm.value;
      console.log('Ticket created:', newTicket);
      // Qui invia il ticket al backend tramite un servizio HTTP
    }
  }
}
