import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NotFound404Component } from './components/not-found-404/not-found-404.component';
import { StatusClassDirective } from './directives/status-class.directive';
import { RouterModule } from '@angular/router';
import { SortCommentsPipe } from './pipes/sort-comments.pipe';

const sharedComponents = [
  CommonModule,
  FormsModule,
  ReactiveFormsModule,
  HttpClientModule,
  RouterModule,
];

const sharedDirectives = [StatusClassDirective];

@NgModule({
  declarations: [NotFound404Component, SortCommentsPipe, ...sharedDirectives],
  imports: [sharedComponents],
  exports: [...sharedComponents, SortCommentsPipe, ...sharedDirectives],
})
export class SharedModule { }
