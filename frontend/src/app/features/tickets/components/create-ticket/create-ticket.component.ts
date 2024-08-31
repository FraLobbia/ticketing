import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ticket, TicketStatus } from '../../../../shared/models/ticket.model';
import { AuthService } from '../../../../core/services/auth.service';
import { TicketService } from '../../services/ticket.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-ticket-create',
  templateUrl: './create-ticket.component.html',
  styleUrls: ['./create-ticket.component.scss'],
})
export class CreateTicketComponent implements OnInit, OnDestroy {
  ticketForm!: FormGroup;
  statuses = Object.values(TicketStatus); // Enum values for the dropdown
  private subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private ticketService: TicketService,
    private router: Router
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
      this.ticketService.createTicket(newTicket).subscribe((ticket) => {
        console.info('Ticket created:', ticket);
        this.router.navigate(['/tickets']);
      });
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
