import { Account } from './account.model';

export interface CommentRequestDto {
  content: string;
  createdAt: Date;
  ticketId: number;
  accountId: number;
}

export interface CommentResponseDto {
  id: number;
  content: string;
  createdAt: Date;
  author: Account;
}
