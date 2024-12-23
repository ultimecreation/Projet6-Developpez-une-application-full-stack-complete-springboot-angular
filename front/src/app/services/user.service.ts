import { inject, Injectable, signal } from '@angular/core';
import { UserRequestInterface } from '../interfaces/UserRequestInterface';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { UpdateProfileRequestInterface } from '../interfaces/UpdateProfileRequestInterface';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private http = inject(HttpClient)
    private bearerToken: string | null = localStorage.getItem('mdd-token')
    isAuthenticated = signal<Boolean>(false)
    getUserInfos() {

        return this.http.get<UserRequestInterface>('http://localhost:8080/api/user/profile', {
            headers: {
                "Authorization": this.bearerToken!
            }
        });
    }

    updateProfile(updateProfileRequest: UpdateProfileRequestInterface) {
        return this.http.put('http://localhost:8080/api/user/profile',
            { ...updateProfileRequest },
            {
                headers: {
                    "Authorization": this.bearerToken!
                }
            }
        )
    }
}
