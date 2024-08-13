import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { HttpClientModule } from '@angular/common/http';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { MaterialModule } from './core/material/material.module';
import { RouterModule } from '@angular/router';
import { TicketModule } from './features/tickets/ticket.module';

const featuresModules = [CoreModule, SharedModule, TicketModule];

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    HttpClientModule,
    MaterialModule,
    ...featuresModules,
  ],
  providers: [provideAnimationsAsync()],
  bootstrap: [AppComponent],
})
export class AppModule {}

//                                                       _           _
//                                                      | |         | |
//    __ _   _ __    _ __        _ __ ___     ___     __| |  _   _  | |   ___
//   / _` | | '_ \  | '_ \      | '_ ` _ \   / _ \   / _` | | | | | | |  / _ \
//  | (_| | | |_) | | |_) |  _  | | | | | | | (_) | | (_| | | |_| | | | |  __/
//   \__,_| | .__/  | .__/  (_) |_| |_| |_|  \___/   \__,_|  \__,_| |_|  \___|
//          | |     | |
//          |_|     |_|
