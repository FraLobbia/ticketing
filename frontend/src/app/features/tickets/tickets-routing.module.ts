import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreateTicketComponent } from './components/create-ticket/create-ticket.component';
import { TicketDetailsComponent } from './components/ticket-details/ticket-details.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
  },
  {
    path: 'create',
    component: CreateTicketComponent,
  },
  {
    path: ':id',
    component: TicketDetailsComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TicketRoutingModule {}
