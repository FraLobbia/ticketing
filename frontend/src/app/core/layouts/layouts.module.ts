import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LayoutsRoutingModule } from './layouts-routing.module';
import { StandardLayoutComponent } from './standard-layout/standard-layout.component';
import { AuthLayoutComponent } from './auth-layout/auth-layout.component';
import { HeaderBarComponent } from './layout-components/header-bar/header-bar.component';
import { SidebarComponent } from './layout-components/sidebar/sidebar.component';
import { MaterialModule } from '../material/material.module';

const layouts = [StandardLayoutComponent, AuthLayoutComponent];
const layoutComponents = [HeaderBarComponent, SidebarComponent];

@NgModule({
  declarations: [...layouts, ...layoutComponents],
  imports: [CommonModule, LayoutsRoutingModule, MaterialModule],
  exports: [...layouts, ...layoutComponents],
})
export class LayoutsModule {}
