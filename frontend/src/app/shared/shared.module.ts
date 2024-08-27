import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../core/material/material.module';
import { NotFound404Component } from './components/not-found-404/not-found-404.component';
import { StatusClassDirective } from './directives/status-class.directive';
import { RouterModule } from '@angular/router';

const sharedComponents = [
  CommonModule,
  FormsModule,
  ReactiveFormsModule,
  HttpClientModule,
  MaterialModule,
  RouterModule,
];

const sharedDirectives = [StatusClassDirective];

@NgModule({
  declarations: [NotFound404Component, ...sharedDirectives],
  imports: [sharedComponents],
  exports: [...sharedComponents, ...sharedDirectives],
})
export class SharedModule {}
