import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

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
      this.authService.register(registrationData).subscribe(
        (response) => {
          // Successo: reindirizza l'utente al login o alla dashboard
          this.router.navigate(['/auth/login']);
        },
        (error) => {
          // Gestisci gli errori di registrazione
          console.error('Registrazione fallita', error);
        }
      );
    }
  }
}
