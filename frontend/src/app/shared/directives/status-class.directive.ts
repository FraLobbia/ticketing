import {
  Directive,
  ElementRef,
  Input,
  OnChanges,
  Renderer2,
} from '@angular/core';
import { TicketStatus } from '../models/ticket.model';

@Directive({
  selector: '[appStatusClass]',
})
export class StatusClassDirective implements OnChanges {
  @Input() appStatusClass!: TicketStatus;

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  ngOnChanges(): void {
    this.updateClass(this.appStatusClass);
  }

  private updateClass(status: TicketStatus): void {
    // Rimuovi le classi esistenti
    this.renderer.removeClass(this.el.nativeElement, 'status-open');
    this.renderer.removeClass(this.el.nativeElement, 'status-pending');
    this.renderer.removeClass(this.el.nativeElement, 'status-in-progress');
    this.renderer.removeClass(this.el.nativeElement, 'status-closed');

    // Aggiungi la classe corretta basata sullo stato
    const className = this.getClassName(status);
    if (className) {
      this.renderer.addClass(this.el.nativeElement, className);
    }
  }

  private getClassName(status: TicketStatus): string {
    switch (status) {
      case TicketStatus.OPEN:
        return 'status-open';
      case TicketStatus.PENDING:
        return 'status-pending';
      case TicketStatus.IN_PROGRESS:
        return 'status-in-progress';
      case TicketStatus.CLOSED:
        return 'status-closed';
      default:
        return '';
    }
  }
}
