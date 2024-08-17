import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../core/material/material.module';
import { NotFound404Component } from './components/not-found-404/not-found-404.component';
import { HoverHighlightDirective } from './directives/hover-highlight.directive';

const sharedComponents = [
  CommonModule,
  FormsModule,
  ReactiveFormsModule,
  HttpClientModule,
  MaterialModule,
];

const sharedDirectives = [HoverHighlightDirective];

@NgModule({
  declarations: [NotFound404Component, ...sharedDirectives],
  imports: [sharedComponents],
  exports: [sharedComponents, ...sharedDirectives],
})
export class SharedModule {}
