import {
  Directive,
  ElementRef,
  Input,
  OnChanges,
  Renderer2,
} from '@angular/core';
import { TicketStatusEnum } from '../models/ticket.model';

@Directive({
  selector: '[appStatusClass]',
})
export class StatusClassDirective implements OnChanges {
  @Input() appStatusClass!: TicketStatusEnum | string;

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  ngOnChanges(): void {
    this.updateClass(this.appStatusClass);
  }

  /**
   * Aggiorna la classe CSS dell'elemento in base allo stato.
   * @param status Lo stato del ticket.
   */
  private updateClass(status: TicketStatusEnum | string): void {
    // Rimuove le classi CSS esistenti (solo quelle relative allo stato)
    Object.keys(TicketStatusEnum).forEach((key) => {
      const className = `status-${key.toLowerCase()}`;
      this.renderer.removeClass(this.el.nativeElement, className);
    });

    // Aggiunge la classe CSS corretta basata sulla chiave dell'enum
    const statusKey = status as keyof typeof TicketStatusEnum;
    const className = `status-${statusKey.toLowerCase()}`;
    if (className) {
      this.renderer.addClass(this.el.nativeElement, className);
    }
  }
}
