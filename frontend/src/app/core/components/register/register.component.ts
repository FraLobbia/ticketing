import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid && this.passwordsMatch()) {
      const { username, password } = this.registerForm.value;
      this.authService.register(username, password).subscribe(
        () => this.router.navigate(['/login']),
        (error) => console.error('Registration failed', error)
      );
    }
  }

  passwordsMatch(): boolean {
    const { password, confirmPassword } = this.registerForm.value;
    return password === confirmPassword;
  }
}
