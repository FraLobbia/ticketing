import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StandardLayoutComponent } from './standard-layout/standard-layout.component';
import { AuthLayoutComponent } from './auth-layout/auth-layout.component';
import { AuthGuard } from '../guards/auth.guard';
import { NotFound404Component } from '../../shared/components/not-found-404/not-found-404.component';

const routes: Routes = [
  {
    path: '',
    component: StandardLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '404',
        component: NotFound404Component,
      },
      {
        path: '**',
        redirectTo: '404', // Reindirizza a una rotta predefinita se l'URL non corrisponde
      },
    ],
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      {
        path: 'login',
        loadChildren: () =>
          import('../login-register-components/login/login.module').then(
            (m) => m.LoginModule
          ),
      },
      {
        path: 'register',
        loadChildren: () =>
          import('../login-register-components/register/register.module').then(
            (m) => m.RegisterModule
          ),
      },
      // Aggiungi altre rotte per l'autenticazione (es. register, forgot-password)
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LayoutsRoutingModule {}
