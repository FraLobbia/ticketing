import { Account } from './account.model';

export enum TicketStatusEnum {
  OPEN = 'Aperto',
  IN_PROGRESS = 'In lavorazione',
  PENDING = 'In attesa',
  CLOSED = 'Chiuso',
}

export interface Ticket {
  id?: number;
  title: string;
  description: string;
  status: TicketStatusEnum;
  account: Account;
  createdAt: Date;
  updatedAt: Date;
}
