import {
  Component,
  EventEmitter,
  Input,
  Output,
  ViewChild,
} from '@angular/core';
import { Ticket } from '../../../../shared/models/ticket.model';
import { Comment } from '../../../../shared/models/comment.model';

@Component({
  selector: 'app-ticket-tab-comments',
  templateUrl: './ticket-tab-comments.component.html',
  styleUrl: './ticket-tab-comments.component.scss',
})
export class TicketTabCommentsComponent {
  @Input() ticket: Ticket | undefined;
  @Input() comments: Comment[] = [];
  @Output() commentAdded = new EventEmitter<void>();

  /**
   * propaga l'evento commentAdded al parent component
   */
  onCommentAdded() {
    this.commentAdded.emit();
  }
}
