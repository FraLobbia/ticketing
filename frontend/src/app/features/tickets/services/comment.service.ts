import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import {
  CommentRequestDto,
  CommentResponseDto,
} from '../../../shared/models/comment.model';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  // costruttore
  constructor(private http: HttpClient) {}

  private baseUrl = 'http://localhost:8080/api/comments';

  /**
   * Richiede al backend di creare un nuovo commento.
   * @param commentRequestDto
   * @returns Observable<CommentResponseDto>
   */
  createComment(
    commentRequestDto: CommentRequestDto
  ): Observable<CommentResponseDto> {
    return this.http.post<CommentResponseDto>(
      `${this.baseUrl}`,
      commentRequestDto
    );
  }

  /**
   * Richiede al backend i commenti relativi a un ticket.
   * @param ticketId
   * @returns Observable<CommentResponseDto[]>
   */
  getCommentsByTicketId(ticketId: number): Observable<CommentResponseDto[]> {
    return this.http.get<CommentResponseDto[]>(
      `${this.baseUrl}/ticket/${ticketId}`
    );
  }

  /**
   * Richiede al backend di eliminare un commento.
   * @param commentId
   * @returns Observable<void>
   */
  deleteComment(commentId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${commentId}`);
  }
}
