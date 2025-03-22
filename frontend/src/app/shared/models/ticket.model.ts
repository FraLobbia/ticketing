import { Account } from './account.model';

export enum TicketStatusEnum {
  OPEN = 'Aperto',
  IN_PROGRESS = 'In Lavorazione',
  PENDING = 'In Attesa',
  CLOSED = 'Chiuso',
}

export interface Ticket {
  id?: number;
  title: string;
  description: string;
  status: TicketStatusEnum;
  author: Account;
  createdAt: Date;
  updatedAt?: Date;
}
