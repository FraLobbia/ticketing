import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../core/material/material.module';
import { NotFound404Component } from './components/not-found-404/not-found-404.component';

const sharedComponents = [
  CommonModule,
  FormsModule,
  ReactiveFormsModule,
  HttpClientModule,
  MaterialModule,
];

@NgModule({
  imports: [sharedComponents],
  exports: [sharedComponents],
  declarations: [NotFound404Component],
})
export class SharedModule {}
