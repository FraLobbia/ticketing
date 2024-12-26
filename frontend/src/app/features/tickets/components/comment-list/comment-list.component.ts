import {
  Component,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { CommentResponseDto } from '../../../../shared/models/comment.model';
import { CommentService } from '../../services/comment.service';
import { Subject, Subscription } from 'rxjs';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.scss'],
})
export class CommentListComponent implements OnInit, OnChanges, OnDestroy {
  /**
   * Variabili
   */
  @Input() ticketId!: number | undefined;
  comments: CommentResponseDto[] = [];
  sortOrder: 'asc' | 'desc' = 'desc';
  showTooltip: number | null = null;

  /**
   * Costruttore
   */
  constructor(private commentService: CommentService) {}

  /**
   * Inizializza il componente
   */
  ngOnInit(): void {
    this.loadComments();
  }

  /**
   * Ricarica i commenti quando avviene un cambiamento. es: nuovo commento.
   */
  ngOnChanges(changes: SimpleChanges): void {
    this.loadComments();
  }

  /**
   * Richiede i commenti per il ticket corrente al backend.
   * I commenti vengono salvati nella variabile comments.
   */
  loadComments() {
    console.log('carica commenti');
    const getCommentsSub = this.commentService
      .getCommentsByTicketId(this.ticketId!)
      .subscribe((comments) => {
        this.comments = comments;
        this.sortComments();
      });
  }

  /**
   * Ordina i commenti in base all'ordine specificato.
   */
  sortComments() {
    if (this.sortOrder === 'asc') {
      this.comments.sort(
        (a, b) =>
          new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime()
      );
    } else {
      this.comments.sort(
        (a, b) =>
          new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
      );
    }
  }

  /**
   * Gestiscono l'evento per mostrare/nascondere il tooltip.
   */
  onMouseEnter(commentId: number): void {
    this.showTooltip = commentId;
  }
  onMouseLeave(): void {
    this.showTooltip = null;
  }

  /**
   * Destroy del componente e delle sottoscrizioni
   */
  destroy$ = new Subject<void>();
  ngOnDestroy(): void {
    this.destroy$.next(); // Emissione del segnale per interrompere le sottoscrizioni
    this.destroy$.complete();
  }
}
