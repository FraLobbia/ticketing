export interface CommentRequestDto {
  content: string;
  createdAt: Date;
  ticketId: number;
  accountId: number;
}

export interface CommentResponseDto {
  id: number;
  accountId: number;
  content: string;
  createdAt: Date;
}
