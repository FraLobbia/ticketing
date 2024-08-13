export enum TicketStatus {
  OPEN = 'OPEN',
  IN_PROGRESS = 'IN_PROGRESS',
  CLOSED = 'CLOSED',
}

export interface Account {
  id: number;
  username: string;
  // Aggiungi altre proprietà rilevanti dell'Account
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
