import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Ticket } from '../../../../shared/models/ticket.model';

@Component({
  selector: 'app-ticket-tab-description',
  templateUrl: './ticket-tab-description.component.html',
  styleUrls: ['./ticket-tab-description.component.scss'],
})
export class TicketTabDescriptionComponent implements OnChanges {
  @Input() ticket?: Ticket;

  // Toggle per mostrare tutta la descrizione
  isExpanded = false;
  // Soglia caratteri prima del "Mostra altro"
  readonly previewLength = 200;

  ngOnChanges(changes: SimpleChanges): void {
    // Quando cambia il ticket, resettiamo il toggle
    if (changes['ticket']) {
      this.isExpanded = false;
    }
  }

  get hasDescription(): boolean {
    return !!this.ticket?.description;
  }

  get isLong(): boolean {
    return !!this.ticket && this.ticket.description.length > this.previewLength;
  }

  get displayedText(): string {
    if (!this.ticket) {
      return '';
    }
    if (this.isExpanded || !this.isLong) {
      return this.ticket.description;
    }
    return this.ticket.description.slice(0, this.previewLength) + '…';
  }

  toggleExpand(): void {
    this.isExpanded = !this.isExpanded;
  }
}
