import { Component, EventEmitter, OnDestroy, Output, ViewChild } from '@angular/core';
import { Comment } from '../../../../shared/models/comment.model';

import {
  Ticket,
  TicketStatusEnum,
} from '../../../../shared/models/ticket.model';
import { TicketService } from '../../services/ticket.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Subject, takeUntil } from 'rxjs';
import { getTicketStatusEnumValue } from '../../../../shared/utility/string-editor.utility';
import { SnackbarService } from '../../../../shared/services/snackbar.service';
import { TicketTabCommentsComponent } from '../ticket-tab-comments/ticket-tab-comments.component';
import { CommentService } from '../../services/comment.service';

@Component({
  selector: 'app-ticket-page-overview',
  templateUrl: './ticket-page-overview.component.html',
  styleUrl: './ticket-page-overview.component.scss',
})
export class TicketPageOverviewComponent implements OnDestroy {
  /**
   * Variabili
   */
  ticket: Ticket | undefined;
  comments: Comment[] = [];
  selectedTab: string = 'overview';
  ticketStatusKeys: string[] = Object.keys(TicketStatusEnum);
  id: number | undefined;
  statusForm: FormGroup = new FormGroup({});
  getTicketStatusEnumValue = getTicketStatusEnumValue;
  @ViewChild('ticketCorrespondence') ticketCorrespondence!: TicketTabCommentsComponent;
  /**
   * Costruttore
   */
  constructor(
    private ticketService: TicketService,
    private commentService: CommentService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private msg: SnackbarService
  ) { }

  /**
   * Inizializza il componente
   */
  ngOnInit(): void {
    this.route.params.pipe(takeUntil(this.destroy$)).subscribe((params) => {
      this.id = +params['id'];
      this.initializeForm();
      this.loadTicketData();
    });
  }

  selectTab(tab: string): void {
    this.selectedTab = tab;
  }

  /**
   * Ottiene i dati del ticket
   */
  loadTicketData(): void {
    this.ticketService
      .getTicketById(this.id!)
      .pipe(takeUntil(this.destroy$))
      .subscribe((data: Ticket) => {
        this.ticket = data;
        this.loadComments(this.ticket);
        this.updateForm();
      });
  }

  /**
  * Richiede i commenti per il ticket corrente al backend.
  * I commenti vengono salvati nella variabile comments.
  */
  loadComments(ticket: Ticket) {
    console.log('carica commenti');
    this.commentService
      .getCommentsByTicketId(ticket.id!)
      .pipe(takeUntil(this.destroy$))
      .subscribe((comments: Comment[]) => {
        this.comments = comments;
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

  deleteTicket(): void {
  }

  /**
   * Evento per cambiare lo stato del ticket.
   * Si iscrive anche al servizio per aggiornare la lista dei ticket visualizzati.
   */
  handleStatusChange(): void {
    this.ticketService
      .updateTicketStatus(this.id!, this.statusForm.value.status)
      .pipe(takeUntil(this.destroy$))
      .subscribe((data: Ticket) => {
        this.ticket = data;
        // this.ticketService.getViewingTicketsFromDB().subscribe((tickets) => {});
      });
  }

  /**
   * Copia l'ID del ticket negli appunti
   */
  copyTicketId(): void {
    if (this.ticket?.id) {
      navigator.clipboard.writeText(this.ticket.id.toString());
      this.msg.show(`Ticket ID ${this.ticket.id} copiato negli appunti`);
    }
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
