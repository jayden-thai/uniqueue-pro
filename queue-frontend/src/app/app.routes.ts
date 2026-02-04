import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Queue } from './components/queue/queue';
import { Register } from './components/register/register';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    { 
        path: '', 
        redirectTo: '/login', 
        pathMatch: 'full' 
    },
    { 
        path: 'login', 
        loadComponent: () => import('./components/login/login').then(m => m.Login) 
    },
    { 
        path: 'register',
        loadComponent: () => import('./components/register/register').then(m => m.Register)
    },
    { 
        path: 'queues/:id', 
        loadComponent: () => import('./components/queue/queue').then(m => m.Queue),
        canActivate: [authGuard] 
    }
];
