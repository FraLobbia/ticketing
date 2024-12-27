import {
  Directive,
  ElementRef,
  Input,
  OnChanges,
  Renderer2,
} from '@angular/core';
import { TicketStatusEnum } from '../models/ticket.model';

/**
 * Direttiva per aggiungere dinamicamente classi CSS in base allo stato di un ticket.
 * - Supporta valori singoli o multipli.
 * - Rimuove automaticamente le classi esistenti prima di aggiungerne di nuove.
 */
@Directive({
  selector: '[appStatusClass]',
})
export class StatusClassDirective implements OnChanges {
  /**
   * Stato del ticket o array di stati.
   * Può essere una stringa (valore singolo) o un array di stringhe (valori multipli).
   */
  @Input() appStatusClass!: TicketStatusEnum | string | string[];

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  /**
   * Rileva i cambiamenti dell'input `appStatusClass` e aggiorna le classi CSS.
   */
  ngOnChanges(): void {
    this.updateClass(this.appStatusClass);
  }

  /**
   * Aggiorna le classi CSS dell'elemento basandosi sullo stato.
   * @param status Stato o stati del ticket da applicare.
   */
  private updateClass(status: TicketStatusEnum | string | string[]): void {
    // Rimuove tutte le classi esistenti relative agli stati
    Object.keys(TicketStatusEnum).forEach((key) => {
      const className = `status-${key.toLowerCase()}`;
      this.renderer.removeClass(this.el.nativeElement, className);
    });

    // Aggiunge nuove classi per uno o più stati
    if (Array.isArray(status)) {
      status.forEach((s) => this.addClass(s));
    } else {
      this.addClass(status);
    }
  }

  /**
   * Aggiunge una singola classe CSS basata sullo stato fornito.
   * @param status Stato del ticket.
   */
  private addClass(status: TicketStatusEnum | string): void {
    if (typeof status === 'string') {
      const className = `status-${status.toLowerCase().replace(/_/g, '-')}`;
      this.renderer.addClass(this.el.nativeElement, className);
    }
  }
}
