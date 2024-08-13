import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreateTicketComponent } from './components/create-ticket/create-ticket.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent, // Componente di default per i ticket
  },
  {
    path: 'create',
    component: CreateTicketComponent, // Componente per creare un nuovo ticket
  },
  // Altre rotte per i ticket
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TicketRoutingModule {}
