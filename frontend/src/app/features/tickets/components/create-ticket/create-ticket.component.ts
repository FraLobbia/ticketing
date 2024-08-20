import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ticket, TicketStatus } from '../../../../shared/models/ticket.model';
import { AuthService } from '../../../../core/services/auth.service';
import { TicketService } from '../../services/ticket.service';

@Component({
  selector: 'app-ticket-create',
  templateUrl: './create-ticket.component.html',
  styleUrls: ['./create-ticket.component.scss'],
})
export class CreateTicketComponent implements OnInit {
  ticketForm!: FormGroup;
  statuses = Object.values(TicketStatus); // Enum values for the dropdown

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private tickerService: TicketService
  ) {}

  ngOnInit(): void {
    const token = this.authService.getToken();

    this.ticketForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      status: TicketStatus.OPEN,
      accountId: this.authService.getUserIdFromToken(),
      createdAt: new Date(),
      updatedAt: new Date(),
    });
  }

  onSubmit(): void {
    if (this.ticketForm.valid) {
      const newTicket: Ticket = this.ticketForm.value;
      this.tickerService.createTicket(newTicket).subscribe((ticket) => {
        console.log('Ticket created:', ticket);
      });
    }
  }
}
