import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LayoutsRoutingModule } from './layouts-routing.module';
import { StandardLayoutComponent } from './standard-layout/standard-layout.component';
import { AuthLayoutComponent } from './auth-layout/auth-layout.component';
import { HeaderBarComponent } from './components/header-bar/header-bar.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { MaterialModule } from '../material/material.module';
import { RouterModule } from '@angular/router';

const layouts = [StandardLayoutComponent, AuthLayoutComponent];
const myLayoutComponents = [HeaderBarComponent, SidebarComponent];

@NgModule({
  declarations: [...layouts, ...myLayoutComponents],
  imports: [CommonModule, RouterModule, LayoutsRoutingModule, MaterialModule],
  exports: [...layouts, ...myLayoutComponents],
})
export class LayoutsModule {}
