import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../../core/material/material.module';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreateTicketComponent } from './components/create-ticket/create-ticket.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TicketRoutingModule } from './tickets-routing.module';
import { TicketTabCommentsComponent } from './components/ticket-tab-comments/ticket-tab-comments.component';
import { TicketPageOverviewComponent } from './components/ticket-page-overview/ticket-page-overview.component';
import { TicketTabDescriptionComponent } from './components/ticket-tab-description/ticket-tab-description.component';
import { CommentFormComponent } from './components/comment-form/comment-form.component';
import { CommentListComponent } from './components/comment-list/comment-list.component';
import { SharedModule } from '../../shared/shared.module';

const ticketComponents = [
  DashboardComponent,
  CreateTicketComponent,
  TicketTabDescriptionComponent,
  TicketTabCommentsComponent,
  TicketPageOverviewComponent,
  CommentFormComponent,
  CommentListComponent,
];

@NgModule({
  declarations: [
    ...ticketComponents,
    TicketTabDescriptionComponent,
    TicketTabCommentsComponent,
    TicketPageOverviewComponent,
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
