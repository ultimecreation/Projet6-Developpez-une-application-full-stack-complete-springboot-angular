import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { ThemeInterface } from '../interfaces/ThemeInterface';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ThemeService {

    private http = inject(HttpClient)
    private bearerToken: string | null = localStorage.getItem('mdd-token')
    isAuthenticated = signal<Boolean>(false)

    getAllThemes(): Observable<{ topics: ThemeInterface[] }> {
        return this.http.get<{ topics: ThemeInterface[] }>('http://localhost:8080/api/topics', {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }

    // getUnsubscribedThemes(): Observable<{ topics: ThemeInterface[] }> {
    //     return this.http.get<{ topics: ThemeInterface[] }>('http://localhost:8080/api/user/topics-to-subscribe', {
    //         headers: {
    //             "Authorization": this.bearerToken!
    //         }
    //     });
    // }

    getSubscribedThemes(): Observable<{ topics: ThemeInterface[] }> {
        return this.http.get<{ topics: ThemeInterface[] }>('http://localhost:8080/api/user/topics', {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }

    removeUserThemeSubscription(themeId: string): Observable<string> {
        return this.http.delete<string>(`http://localhost:8080/api/user/topics/remove/${themeId}`, {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }

    addUserThemeSubscription(themeId: string): Observable<Object> {
        return this.http.post(`http://localhost:8080/api/user/topics/add/${themeId}`, null, {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }


}
