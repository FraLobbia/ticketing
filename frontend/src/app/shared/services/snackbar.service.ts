import { Injectable } from '@angular/core';
import {
  MatSnackBar,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class SnackbarService {
  /**
   * Property
   */
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  defaultAction: string = 'Chiudi';
  defaultDuration: number = 3000;
  defaultErrorDuration: number = 5000;

  constructor(private snackBar: MatSnackBar) { }

  /**
   * Mostra uno snackbar con il messaggio specificato.
   * @param message Messaggio da visualizzare.
   * @param action Azione da visualizzare come pulsante
   * @param duration Durata dello snackbar in millisecondi
   */
  show(
    message: string,
    action: string = this.defaultAction,
    duration: number = this.defaultDuration
  ): void {
    this.snackBar.open(message, action, {
      duration,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
    });
  }

  /**
   * Mostra uno snackbar di errore con il messaggio specificato.
   * @param message - Messaggio da visualizzare
   * @param action - Azione da visualizzare come pulsante
   * @param duration - Durata dello snackbar in millisecondi
   */
  showError(
    message: string,
    action: string = this.defaultAction,
    duration: number = this.defaultErrorDuration
  ): void {
    this.snackBar.open(message, action, {
      duration,
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,

    });
  }
}
