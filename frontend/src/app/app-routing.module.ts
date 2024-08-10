import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFound404Component } from './shared/components/not-found-404/not-found-404.component';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginComponent } from './core/components/login/login.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '',
  },
  {
    path: '',
    component: LoginComponent,
  },
  // {
  //   path: 'about',
  //   component: AboutComponent,
  // },
  // {
  //   path: 'contact',
  //   component: ContactComponent,
  //   canActivate: [AuthGuard],
  //   canActivateChild: [AuthGuard],
  //   children: [{ path: ':id', component: PersonaComponent }],
  // },
  {
    path: '404',
    component: NotFound404Component,
  },
  {
    path: '**',
    redirectTo: '404',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
