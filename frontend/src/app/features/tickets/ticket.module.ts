import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../../core/material/material.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreateTicketComponent } from './components/create-ticket/create-ticket.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TicketRoutingModule } from './tickets-routing.module';
import { TicketCorrespondenceComponent } from './components/ticket-correspondence/ticket-correspondence.component';
import { SingleTicketPageComponent } from './components/single-ticket-page/single-ticket-page.component';
import { TicketOverviewComponent } from './components/ticket-overview/ticket-overview.component';
import { CommentFormComponent } from './components/comment-form/comment-form.component';
import { CommentListComponent } from './components/comment-list/comment-list.component';
import { SharedModule } from '../../shared/shared.module';

const ticketComponents = [
  DashboardComponent,
  CreateTicketComponent,
  TicketOverviewComponent,
  TicketCorrespondenceComponent,
  SingleTicketPageComponent,
  CommentFormComponent,
  CommentListComponent,
];

@NgModule({
  declarations: [
    ...ticketComponents,
    TicketOverviewComponent,
    TicketCorrespondenceComponent,
    SingleTicketPageComponent,
    CommentFormComponent,
    CommentListComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    TicketRoutingModule,
    SharedModule,
  ],
  exports: [...ticketComponents],
})
export class TicketModule {}
