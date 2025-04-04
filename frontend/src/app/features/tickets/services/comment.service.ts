import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment } from '../../../shared/models/comment.model';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  // costruttore
  constructor(private http: HttpClient) { }

  private baseUrl = 'http://localhost:8080/api/comments';

  /**
   * Richiede al backend di creare un nuovo commento.
   * @param commentRequestDto
   * @returns Observable<CommentResponseDto>
   */
  createComment(
    commentRequestDto: Comment
  ): Observable<Comment> {
    return this.http.post<Comment>(
      `${this.baseUrl}`,
      commentRequestDto
    );
  }

  /**
   * Richiede al backend i commenti relativi a un ticket.
   * @param ticketId
   * @returns Observable<CommentResponseDto[]>
   */
  getCommentsByTicketId(ticketId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(
      `${this.baseUrl}/ticket/${ticketId}`
    );
  }

  /**
   * Richiede al backend di eliminare un commento.
   * @param id
   * @returns Observable<void>
   */
  deleteComment(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
