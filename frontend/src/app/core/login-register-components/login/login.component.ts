import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { ILogin } from '../../../shared/interfaces/auth.interface';
import { Subject, Subscription, take, takeUntil } from 'rxjs';
import { SnackbarService } from '../../../shared/services/snackbar.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnDestroy {

  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private msg: SnackbarService
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const loginData: ILogin = this.loginForm.value;
      this.authService.login(loginData).pipe(takeUntil(this.destroy$))

        .subscribe(
          {
            next: () => this.router.navigate(['/tickets']),
          }
        );
    }
  }

  fillInputs(): void {
    this.loginForm.patchValue({
      email: 'test@test.com',
      password: 'qwerty',
    });
  }

  /**
   * Destroy del componente e delle sottoscrizioni
   */
  destroy$ = new Subject<void>();
  ngOnDestroy(): void {
    this.destroy$.next(); // Emissione del segnale per interrompere le sottoscrizioni
    this.destroy$.complete();
  }
}
