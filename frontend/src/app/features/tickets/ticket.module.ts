import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../../core/material/material.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreateTicketComponent } from './components/create-ticket/create-ticket.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TicketRoutingModule } from './tickets-routing.module';

const ticketComponents = [DashboardComponent, CreateTicketComponent];

@NgModule({
  declarations: [...ticketComponents],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    TicketRoutingModule,
  ],
  exports: [...ticketComponents],
})
export class TicketModule {}
