import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms'; 
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  authService = inject(AuthService);
  router = inject(Router);

  email = '';
  password = '';
  errorMessage = '';

  // Runs when user clicks "Login"
  onLogin() {
    this.authService.login(this.email, this.password).subscribe({
      next: (user) => {
        console.log("Logged in as: ", user);
        this.router.navigate(['/queue']);
      },
      error: (err) => {
        this.errorMessage = "Invalid credentials";
        console.error(err);
      }
    });

    
  }

}
