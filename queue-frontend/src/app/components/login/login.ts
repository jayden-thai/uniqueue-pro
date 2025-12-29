import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms'; 
import { Router } from '@angular/router';
import { QueueService } from '../../services/queue.service';

@Component({
  selector: 'app-login',
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  // Definite properties to store user input
  username = '';
  password = '';

  // Inject Router for navigation
  constructor(
    private router: Router,
    private queueService: QueueService
  ) {}

  // Runs when user clicks "Login"
  onLogin() {
    if (this.username) {
      console.log('Logging in as:', this.username);

      this.queueService.login(this.username);

      this.router.navigate(['/queue']);
    }

    
  }

}
