import { TicketStatusEnum } from '../models/ticket.model';

export function getTicketStatusEnumValueOld(status: string): string {
  const statusKey = status as keyof typeof TicketStatusEnum;
  return TicketStatusEnum[statusKey];
}


/**
 * Restituisce le classi badge Bootstrap in base allo status del ticket.
 */
export function getStatusBadgeClass(status: TicketStatusEnum | string): string {
  switch (status) {
    case TicketStatusEnum.OPEN:
      return 'status-open ';
    case TicketStatusEnum.IN_PROGRESS:
      return 'status-in-progress ';
    case TicketStatusEnum.PENDING:
      return 'status-pending ';
    case TicketStatusEnum.CLOSED:
      return 'status-closed ';
    default:
      return 'status-sconosciuto';
  }
}

/**
 * Restituisce la label leggibile per un valore di enum.
 */
export function getTicketStatusEnumValue(status: TicketStatusEnum | string): string {
  return status as string;
}
