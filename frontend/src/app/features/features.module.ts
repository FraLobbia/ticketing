import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicketModule } from './tickets/ticket.module';

const featureModules = [TicketModule];

@NgModule({
  declarations: [],
  imports: [CommonModule, ...featureModules],
  exports: [...featureModules],
})
export class FeaturesModule {}
