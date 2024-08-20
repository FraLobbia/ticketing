import {
  Component,
  EventEmitter,
  Input,
  Output,
  ViewChild,
} from '@angular/core';
import { Ticket } from '../../../../shared/models/ticket.model';
import { CommentListComponent } from '../comment-list/comment-list.component';

@Component({
  selector: 'app-ticket-correspondence',
  templateUrl: './ticket-correspondence.component.html',
  styleUrl: './ticket-correspondence.component.scss',
})
export class TicketCorrespondenceComponent {
  @Input() ticket: Ticket | undefined;
  /**
   * commentAdded è un evento emesso quando un commento viene aggiunto dal componente figlio CommentFormComponent
   */
  @Output() commentAdded = new EventEmitter<void>();
  @Output() ticketStatusChanged = new EventEmitter<void>();
  /**
   * Riferimento al componente figlio CommentListComponent per poter chiamare il metodo loadComments()
   */
  @ViewChild(CommentListComponent)
  commentListComponent!: CommentListComponent;

  constructor() {}
  /**
   * Metodo per notificare il parent component dell'aggiunta di un commento e ricaricare la lista dei commenti nel CommentListComponent
   */
  onCommentAdded() {
    // Esegui delle azioni quando un commento viene aggiunto, ad esempio ricaricare la lista dei commenti
    this.commentListComponent.loadComments();
  }

  /**
   * Metodo per notificare il parent component del cambio di stato del ticket
   */
  onTicketStatusChanged() {
    this.ticketStatusChanged.emit();
  }
}
