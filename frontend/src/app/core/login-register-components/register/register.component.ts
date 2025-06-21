import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
  ValidationErrors,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Subject, throwError } from 'rxjs';
import { takeUntil, finalize, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit, OnDestroy {
  fg!: FormGroup;
  isLoading = false;
  showPassword = false;
  showConfirmPassword = false;
  private destroy$ = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.fg = this.fb.group(
      {
        name: ['', Validators.required],
        surname: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        newPassword: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', Validators.required],
      },
      { validators: this.passwordsMatch }
    );
  }

  private passwordsMatch(group: AbstractControl): ValidationErrors | null {
    const pass = group.get('newPassword')?.value;
    const confirm = group.get('confirmPassword')?.value;

    return pass === confirm ? null : { passwordMismatch: true };
  }

  /** Ritorna i controlli del form */
  get fc() {
    return this.fg.controls;
  }

  /** Controlla se un controllo ha un certo errore ed è stato toccato */
  public hasError(controlName: string, errorName: string): boolean {
    const ctrl = this.fc[controlName];
    return !!(ctrl && ctrl.touched && ctrl.errors?.[errorName]);
  }

  get errors(): ValidationErrors | null {
    return this.fg.errors;
  }


  onSubmit(): void {
    if (this.fg.invalid) {
      this.fg.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    const data = {
      name: this.fg.controls['name'].value,
      surname: this.fg.controls['surname'].value,
      email: this.fg.controls['email'].value,
      password: this.fg.controls['password'].value,
    };

    this.authService
      .register(data)
      .pipe(
        takeUntil(this.destroy$),
        finalize(() => (this.isLoading = false)),
        catchError((err) => {
          console.error(err);
          return throwError(err);
        })
      )
      .subscribe(() => {
        this.router.navigate(['/tickets']);
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
