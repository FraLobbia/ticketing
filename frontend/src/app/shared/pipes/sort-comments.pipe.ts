import { Pipe, PipeTransform } from '@angular/core';
import { Comment } from '../models/comment.model';


@Pipe({
  name: 'sortComments'
})
export class SortCommentsPipe implements PipeTransform {
  transform(comments: Comment[], sortOrder: 'asc' | 'desc' = 'desc'): Comment[] {
    if (!comments || comments.length === 0) return [];
    return comments.slice().sort((a, b) => {
      const dateA = new Date(a.createdAt!).getTime();
      const dateB = new Date(b.createdAt!).getTime();
      return sortOrder === 'asc' ? dateA - dateB : dateB - dateA;
    });
  }
}
