import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, throwError } from 'rxjs';
import { takeUntil, finalize, catchError } from 'rxjs/operators';

import { Ticket, TicketStatusEnum } from '../../../../shared/models/ticket.model';
import { Comment } from '../../../../shared/models/comment.model';
import { TicketService } from '../../services/ticket.service';
import { CommentService } from '../../services/comment.service';
import { SnackbarService } from '../../../../shared/services/snackbar.service';
import { getTicketStatusEnumValue, getStatusBadgeClass } from '../../../../shared/utility/string-editor.utility';
@Component({
  selector: 'app-ticket-page-overview',
  templateUrl: './ticket-page-overview.component.html',
  styleUrls: ['./ticket-page-overview.component.scss'],
})
export class TicketPageOverviewComponent implements OnInit, OnDestroy {
  ticket?: Ticket;
  comments: Comment[] = [];
  selectedTab: 'overview' | 'comments' = 'overview';
  isEditMode = false;
  isLoading = true;
  isStatusLoading = false;
  isDeleting = false;

  ticketStatusKeys = Object.keys(TicketStatusEnum);
  statusForm!: FormGroup;

  private id?: number;
  private destroy$ = new Subject<void>();

  // esponi la funzione di utility
  public getTicketStatusEnumValue = getTicketStatusEnumValue;
  public getStatusBadgeClass = getStatusBadgeClass;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private ticketService: TicketService,
    private commentService: CommentService,
    private msg: SnackbarService
  ) { }

  ngOnInit(): void {
    this.statusForm = this.fb.group({ status: [''] });
    this.route.params
      .pipe(takeUntil(this.destroy$))
      .subscribe(params => {
        this.id = +params['id'];
        this.loadTicket();
      });
  }

  private loadTicket(): void {
    if (!this.id) return;
    this.isLoading = true;
    this.ticketService.getTicketById(this.id)
      .pipe(finalize(() => this.isLoading = false), takeUntil(this.destroy$))
      .subscribe({
        next: t => {
          this.ticket = t;
          this.statusForm.patchValue({ status: t.status });
          this.loadComments();
        },
        error: () => this.msg.show('Impossibile caricare il ticket')
      });
  }

  // ora pubblico
  public loadComments(): void {
    if (!this.ticket?.id) return;
    this.commentService.getCommentsByTicketId(this.ticket.id)
      .pipe(takeUntil(this.destroy$))
      .subscribe(cs => this.comments = cs);
  }


  public toggleEdit(): void {
    this.isEditMode = !this.isEditMode;
    this.msg.show(this.isEditMode ? 'Modalità modifica attivata' : 'Modalità modifica disattivata');
  }


  onStatusChange(): void { }
  confirmDelete(): void { }
  deleteTicket(): void { }


  public copyTicketId(): void {
    if (this.ticket?.id) {
      navigator.clipboard.writeText(this.ticket.id.toString());
      this.msg.show(`ID ticket ${this.ticket.id} copiato`);
    }
  }

  get statusControl() {
    return this.statusForm.get('status')!;
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
