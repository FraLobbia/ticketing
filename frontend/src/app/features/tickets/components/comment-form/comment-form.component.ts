import {
  Component,
  Input,
  Output,
  EventEmitter,
  OnInit,
  OnDestroy,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommentService } from '../../services/comment.service';
import { AuthService } from '../../../../core/services/auth.service';
import { Subject, throwError } from 'rxjs';
import { takeUntil, finalize, catchError } from 'rxjs/operators';
import { Ticket } from '../../../../shared/models/ticket.model';
import { Comment } from '../../../../shared/models/comment.model';

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.scss'],
})
export class CommentFormComponent implements OnInit, OnDestroy {
  @Input() ticket?: Ticket;
  @Output() commentAdded = new EventEmitter<void>();

  commentForm: FormGroup;
  isLoading = false;
  private destroy$ = new Subject<void>();
  private authorId?: number;

  constructor(
    private fb: FormBuilder,
    private commentService: CommentService,
    private authService: AuthService
  ) {
    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.minLength(3)]],
    });
  }

  ngOnInit(): void {
    const payload = this.authService.getTokenPayload();
    if (payload?.idAccount) {
      this.authorId = payload.idAccount;
    } else {
      console.error('Account id non trovato');
    }
  }

  get content() {
    return this.commentForm.get('content');
  }

  onSubmit(): void {
    if (!this.ticket || !this.authorId) {
      console.error('Ticket o authorId mancanti');
      return;
    }

    if (this.commentForm.invalid) {
      this.commentForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const newComment: Comment = {
      content: this.content!.value,
      ticket: this.ticket,
      author: { id: this.authorId },
    };

    this.commentService.createComment(newComment)
      .pipe(
        takeUntil(this.destroy$),
        finalize(() => this.isLoading = false),
        catchError(err => {
          console.error('Errore creazione commento', err);
          return throwError(err);
        })
      )
      .subscribe(() => {
        this.commentForm.reset();
        this.commentAdded.emit();
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
