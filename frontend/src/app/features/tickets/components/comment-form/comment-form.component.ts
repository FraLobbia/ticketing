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
import { CommentRequestDto } from '../../../../shared/models/comment.model';
import { AuthService } from '../../../../core/services/auth.service';
import { Subject, Subscription } from 'rxjs';

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.scss'],
})
export class CommentFormComponent implements OnDestroy {
  /**
   * Variabili
   */
  @Input() ticketId!: number | undefined; //Input per ricevere l'id del ticket e dell'account dal parent component
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
    const accountId = this.authService.getUserIdFromToken();
    if (!accountId) {
      console.error('Account id not found');
      return;
    }

    if (this.commentForm.valid && accountId && this.ticketId) {
      const newComment: CommentRequestDto = {
        content: this.commentForm.value.content,
        createdAt: new Date(),
        ticketId: this.ticketId, //TODO
        accountId: +accountId,
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
