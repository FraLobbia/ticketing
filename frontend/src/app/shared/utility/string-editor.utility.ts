import { TicketStatusEnum } from '../models/ticket.model';

export function getTicketStatusEnumValue(status: string): string {
  const statusKey = status as keyof typeof TicketStatusEnum;
  return TicketStatusEnum[statusKey];
}
