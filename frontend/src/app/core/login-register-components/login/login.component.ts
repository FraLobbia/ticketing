import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { ILogin } from '../../../shared/interfaces/auth.interface';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnDestroy {
  fg: FormGroup;
  showPassword = false;
  private destroy$ = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.fg = this.fb.group({
      email:    ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  get fc() {
    return this.fg.controls;
  }

  /** Controlla se un controllo ha un certo errore ed è stato toccato */
  public hasError(controlName: string, errorName: string): boolean {
    const ctrl = this.fc[controlName];
    return !!(ctrl && ctrl.touched && ctrl.errors?.[errorName]);
  }

  onSubmit(): void {
    if (this.fg.invalid) {
      this.fg.markAllAsTouched();
      return;
    }
    const loginData: ILogin = this.fg.value;
    this.authService
      .login(loginData)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.router.navigate(['/tickets']);
      });
  }

  /**
   * Popola i campi del form con dati di esempio.
   * Utilizzato per testare il form senza dover digitare manualmente.
   * TODO: Rimuovere
   */
  fillInputs(): void {
    this.fg.patchValue({
      email: 'test@test.com',
      password: 'qwerty',
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
