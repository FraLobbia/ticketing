import { Account } from './account.model';

export enum TicketStatus {
  OPEN = 'OPEN',
  IN_PROGRESS = 'IN_PROGRESS',
  PENDING = 'PENDING',
  CLOSED = 'CLOSED',
}

export interface Ticket {
  id?: number;
  title: string;
  description: string;
  status: TicketStatus;
  account: Account;
  createdAt: Date;
  updatedAt: Date;
}
