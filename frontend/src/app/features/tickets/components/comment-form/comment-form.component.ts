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
import { Subject, takeUntil } from 'rxjs';
import { Ticket } from '../../../../shared/models/ticket.model';
import { Comment } from '../../../../shared/models/comment.model';
import { SnackbarService } from '../../../../shared/services/snackbar.service';

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.scss'],
})
export class CommentFormComponent implements OnInit, OnDestroy {
  /**
   * Variabili
   */
  authorId: number | undefined;
  @Input() ticket: Ticket | undefined;
  @Output() commentAdded = new EventEmitter<void>(); //Evento emesso al parent quando un commento viene aggiunto
  commentForm: FormGroup;

  /**
   * Costruttore
   */
  constructor(
    private fb: FormBuilder,
    private commentService: CommentService,
    private authService: AuthService,
    private msgService: SnackbarService

  ) {
    this.commentForm = this.fb.group({
      content: ['', Validators.required],
    });
  }
  ngOnInit(): void {
    const id = this.authService.getTokenPayload()?.idAccount;
    if (id) {
      this.authorId = id;
    } else {
      console.error('Account id not found');
      this.msgService.showError('Non sei loggato');
    }
  }


  onSubmit(): void {
    if (this.commentForm.valid && this.authorId && this.ticket) {
      const comment: Comment = {
        content: this.commentForm.value.content,
        ticket: this.ticket,
        author: { id: this.authorId },
      };
      this.commentService
        .createComment(comment)
        .pipe(takeUntil(this.destroy$))
        .subscribe(() => {
          this.commentAdded.emit();
          this.commentForm.reset();
        });
    } else {
      console.error('Form is not valid');
      this.msgService.showError('Il form non è valido');
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
