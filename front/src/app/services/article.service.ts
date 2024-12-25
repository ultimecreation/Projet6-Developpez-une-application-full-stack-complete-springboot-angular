import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleInterface } from '../interfaces/ArticleInterface';
import { ArticleAddRequestInterface } from '../interfaces/ArticleAddRequestInterface';

@Injectable({
    providedIn: 'root'
})
export class ArticleService {

    private http = inject(HttpClient)
    private bearerToken: string | null = localStorage.getItem('mdd-token')
    isAuthenticated = signal<Boolean>(false)

    getAllPosts(): Observable<{ posts: ArticleInterface[] }> {
        return this.http.get<{ posts: ArticleInterface[] }>('http://localhost:8080/api/posts', {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }

    getUserFeed(): Observable<{ posts: ArticleInterface[] }> {
        return this.http.get<{ posts: ArticleInterface[] }>('http://localhost:8080/api/user/feed', {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }

    getPostById(id: string): Observable<ArticleInterface> {
        return this.http.get<ArticleInterface>(`http://localhost:8080/api/posts/${id}`, {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }

    save(articleAddRequest: ArticleAddRequestInterface): Observable<ArticleInterface> {
        return this.http.post<ArticleInterface>(`http://localhost:8080/api/posts`, articleAddRequest, {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }
}
