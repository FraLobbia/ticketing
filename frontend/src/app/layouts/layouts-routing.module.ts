// // layouts-routing.module.ts
// import { NgModule } from '@angular/core';
// import { RouterModule, Routes } from '@angular/router';
// import { AdminLayoutComponent } from './admin-layout/admin-layout.component';
// import { UserLayoutComponent } from './user-layout/user-layout.component';
// import { AuthLayoutComponent } from './auth-layout/auth-layout.component';

// const routes: Routes = [
//   {
//     path: '',
//     component: AdminLayoutComponent,
//     children: [
//       {
//         path: 'admin',
//         loadChildren: () =>
//           import('../features/admin/admin.module').then((m) => m.AdminModule),
//       },
//     ],
//   },
//   {
//     path: '',
//     component: UserLayoutComponent,
//     children: [
//       {
//         path: 'user',
//         loadChildren: () =>
//           import('../features/user/user.module').then((m) => m.UserModule),
//       },
//     ],
//   },
//   {
//     path: '',
//     component: AuthLayoutComponent,
//     children: [
//       {
//         path: 'auth',
//         loadChildren: () =>
//           import('../features/auth/auth.module').then((m) => m.AuthModule),
//       },
//     ],
//   },
// ];

// @NgModule({
//   imports: [RouterModule.forChild(routes)],
//   exports: [RouterModule],
// })
// export class LayoutsRoutingModule {}
