import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleInterface } from '../interfaces/ArticleInterface';
import { CommentInterface } from '../interfaces/CommentInterface';
import { CommentRequestInterface } from '../interfaces/CommentRequestInterface';

@Injectable({
    providedIn: 'root'
})
export class CommentService {

    private http = inject(HttpClient)
    private bearerToken: string | null = localStorage.getItem('mdd-token')
    isAuthenticated = signal<Boolean>(false)

    getAllCommentsByArticleId(articleId: string): Observable<{ comments: CommentInterface[] }> {
        return this.http.get<{ comments: CommentInterface[] }>(`http://localhost:8080/api/posts/${articleId}/comments`, {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }


    saveComment(comment: CommentRequestInterface): Observable<any> {
        return this.http.post(`http://localhost:8080/api/comments`, comment, {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }


}
