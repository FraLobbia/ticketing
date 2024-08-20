import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { CommentResponseDto } from '../../../../shared/models/comment.model';
import { CommentService } from '../../services/comment.service';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.scss'],
})
export class CommentListComponent implements OnInit, OnChanges {
  @Input() ticketId!: number | undefined;
  comments: CommentResponseDto[] = [];

  constructor(private commentService: CommentService) {}

  ngOnInit(): void {
    this.loadComments();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.loadComments();
  }

  /**
   * Richiede i commenti per il ticket corrente al backend.
   * I commenti vengono salvati nella variabile comments.
   */
  loadComments() {
    console.log('carica commenti');
    this.commentService
      .getCommentsByTicketId(this.ticketId!)
      .subscribe((comments) => {
        this.comments = comments;
      });
  }
}
