import {
  Component,
  Input
} from '@angular/core';
import { Comment } from '../../../../shared/models/comment.model';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.scss'],
})
export class CommentListComponent {
  delete() {
    throw new Error('Method not implemented.');
  }
  @Input() comments: Comment[] = [];
  sortOrder: 'asc' | 'desc' = 'desc';
  showTooltip: number | null = null;
  isEditMode: boolean = false;

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
