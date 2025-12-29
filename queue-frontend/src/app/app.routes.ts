import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Queue } from './components/queue/queue';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: Login },
    { 
        path: 'queue', 
        component: Queue,
        canActivate: [authGuard] 
    },
];
