import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Ticket, TicketStatusEnum } from '../../../../shared/models/ticket.model';
import { AuthService } from '../../../../core/services/auth.service';
import { TicketService } from '../../services/ticket.service';
import { Router } from '@angular/router';
import { Subject, throwError } from 'rxjs';
import { finalize, takeUntil, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-ticket-create',
  templateUrl: './create-ticket.component.html',
  styleUrls: ['./create-ticket.component.scss'],
})
export class CreateTicketComponent implements OnInit, OnDestroy {
  ticketForm!: FormGroup;
  statuses = Object.values(TicketStatusEnum);
  isLoading = false;
  private destroy$ = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private ticketService: TicketService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ticketForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(5)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
    });
  }

  onSubmit(): void {
    if (this.ticketForm.invalid) {
      this.ticketForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const payload: Ticket = {
      ...this.ticketForm.value,
      author: { id: this.authService.getTokenPayload()!.idAccount },
    };

    this.ticketService.create(payload)
      .pipe(
        takeUntil(this.destroy$),
        finalize(() => this.isLoading = false),
        catchError(err => {
          console.error('Errore creazione ticket', err);
          // potresti mostrare un toast o un alert qui
          return throwError(err);
        })
      )
      .subscribe(ticket => {
        this.router.navigate(['/tickets', ticket.id]);
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  // convenience getters
  get title() { return this.ticketForm.get('title'); }
  get description() { return this.ticketForm.get('description'); }
}
