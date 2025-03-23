import {
  Component,
  Input,
  OnInit
} from '@angular/core';
import { Comment } from '../../../../shared/models/comment.model';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.scss'],
})
export class CommentListComponent {
  @Input() comments: Comment[] = [];
  sortOrder: 'asc' | 'desc' = 'desc';
  showTooltip: number | null = null;

  /**
   * Gestiscono l'evento per mostrare/nascondere il tooltip sopra il nome dell'utente
   */
  onMouseEnter(commentId: number): void {
    this.showTooltip = commentId;
  }
  onMouseLeave(): void {
    this.showTooltip = null;
  }
}
