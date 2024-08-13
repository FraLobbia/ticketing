import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from '../../shared/shared.module';
import { MaterialModule } from '../../core/material/material.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, SharedModule, MaterialModule],
})
export class UsersModule {}
