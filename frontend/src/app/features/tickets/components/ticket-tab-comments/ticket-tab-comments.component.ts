import {
  Component,
  EventEmitter,
  Input,
  Output,
  ViewChild,
} from '@angular/core';
import { Ticket } from '../../../../shared/models/ticket.model';
import { CommentListComponent } from '../comment-list/comment-list.component';
import { Comment } from '../../../../shared/models/comment.model';

@Component({
  selector: 'app-ticket-tab-comments',
  templateUrl: './ticket-tab-comments.component.html',
  styleUrl: './ticket-tab-comments.component.scss',
})
export class TicketTabCommentsComponent {
  @Input() ticket: Ticket | undefined;
  @Input() comments: Comment[] = [];
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


  /**
   * Metodo per notificare il parent component dell'aggiunta di un commento e ricaricare la lista dei commenti nel CommentListComponent
   */
  onCommentAdded() {
    // Esegui delle azioni quando un commento viene aggiunto, ad esempio ricaricare la lista dei commenti
    // this.commentListComponent.loadComments();
  }

  // /**
  //  * Metodo per notificare il parent component del cambio di stato del ticket
  //  */
  // onTicketStatusChanged() {
  //   this.ticketStatusChanged.emit();
  // }
}
