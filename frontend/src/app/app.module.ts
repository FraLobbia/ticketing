import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProvaComponent } from './prova/prova.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [AppComponent, ProvaComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
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
