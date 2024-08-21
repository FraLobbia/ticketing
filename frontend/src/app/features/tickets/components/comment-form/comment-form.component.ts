import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommentService } from '../../services/comment.service';
import { CommentRequestDto } from '../../../../shared/models/comment.model';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-comment-form',
  templateUrl: './comment-form.component.html',
  styleUrls: ['./comment-form.component.scss'],
})
export class CommentFormComponent implements OnInit {
  /**
   * Input per ricevere l'id del ticket e dell'account dal parent component
   */
  @Input() ticketId!: number | undefined;
  /**
   * Evento emesso al parent quando un commento viene aggiunto
   */
  @Output() commentAdded = new EventEmitter<void>();

  commentForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private commentService: CommentService,
    private authService: AuthService
  ) {
    this.commentForm = this.fb.group({
      content: ['', Validators.required],
    });
  }
  ngOnInit(): void {
    console.log(this.ticketId);
  }

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

      this.commentService.createComment(newComment).subscribe(() => {
        this.commentAdded.emit(); // Notifica il parent component dell'aggiunta di un commento
        this.commentForm.reset(); // Reset del form
      });
    } else console.error('Form is not valid');
  }
}
