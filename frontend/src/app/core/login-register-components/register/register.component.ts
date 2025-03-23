import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { SnackbarService } from '../../../shared/services/snackbar.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit, OnDestroy {
  registerForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private msg: SnackbarService
  ) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      const registrationData = this.registerForm.value;

      // Simula la registrazione inviando i dati al servizio di autenticazione
      this.authService.register(registrationData).pipe(
        takeUntil(this.destroy$))
        .subscribe(
          {
            next: () => this.router.navigate(['/tickets']),
            error: (error) => {
              console.error(error);
              this.msg.show(error, 'Chiudi', 5000);
            },
          }
        );
    }
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
