import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthLayoutComponent } from './core/layouts/auth-layout/auth-layout.component';
import { StandardLayoutComponent } from './core/layouts/standard-layout/standard-layout.component';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: StandardLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'tickets',
        loadChildren: () =>
          import('../app/features/tickets/ticket.module').then(
            (m) => m.TicketModule
          ),
      },
      // Aggiungi qui altre rotte per moduli protetti
    ],
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      {
        path: 'login',
        loadChildren: () =>
          import('./core/login-register-components/login/login.module').then(
            (m) => m.LoginModule
          ),
      },
      {
        path: 'register',
        loadChildren: () =>
          import(
            './core/login-register-components/register/register.module'
          ).then((m) => m.RegisterModule),
      },
      // Aggiungi altre rotte per l'autenticazione (es. register, forgot-password)
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
