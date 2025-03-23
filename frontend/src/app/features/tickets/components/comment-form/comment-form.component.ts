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
import { Subject } from 'rxjs';
import { Ticket } from '../../../../shared/models/ticket.model';
import { Comment } from '../../../../shared/models/comment.model';

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.scss'],
})
export class CommentFormComponent implements OnDestroy {
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
    private authService: AuthService
  ) {
    this.commentForm = this.fb.group({
      content: ['', Validators.required],
    });
  }

  /**
   * Metodo per inviare il commento al server
   */
  onSubmit() {
    const id = this.authService.getTokenPayload()?.idAccount;
    if (!id) {
      console.error('Account id not found');
      return;
    } else {
      this.authorId = id;
    }

    if (this.commentForm.valid && this.authorId && this.ticket) {
      const newComment: Comment = {
        content: this.commentForm.value.content,
        ticket: this.ticket,
        author: { id: this.authorId },
      };
      const createCommentSub = this.commentService
        .createComment(newComment)
        .subscribe(() => {
          this.commentAdded.emit(); // Notifica il parent component dell'aggiunta di un commento
          this.commentForm.reset(); // Reset del form
        });
    } else console.error('Form is not valid');
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
