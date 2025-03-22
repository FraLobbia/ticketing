import { Account } from './account.model';
import { Ticket } from './ticket.model';

export interface Comment {
  id?: number;
  content: string;
  author: Account;
  createdAt?: Date;
  updatedAt?: Date;
  ticket: Ticket;
}
