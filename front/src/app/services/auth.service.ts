import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { LoginRequestInterface } from '../interfaces/LoginRequestInterface';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';
import { UserRequestInterface } from '../interfaces/UserRequestInterface';
import { RegisterRequestInterface } from '../interfaces/RegisterRequestInterface';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private http = inject(HttpClient)
    private router = inject(Router)
    private bearerToken: string | null = localStorage.getItem('mdd-token')
    private decodedToken: any = {}
    isAuthenticated = signal<Boolean>(false)

    login(loginRequest: LoginRequestInterface) {
        return this.http.post('http://localhost:8080/api/auth/login', { ...loginRequest })
    }

    register(registerRequest: RegisterRequestInterface) {
        return this.http.post('http://localhost:8080/api/auth/register', { ...registerRequest })
    }

    logout() {
        localStorage.removeItem('mdd-token')
        this.bearerToken = null
        this.isAuthenticated.set(false)
        return this.router.navigateByUrl('/')
    }

    saveUserToLocalStorage(token: string) {
        this.decodedToken = jwtDecode(token)
        if (this.tokenIsExpired(this.decodedToken.exp)) return false
        if (localStorage.getItem('mdd-token') !== null) {
            localStorage.removeItem('mdd-token')
        }
        localStorage.setItem('mdd-token', `Bearer ${token}`)
        this.bearerToken = `Bearer ${token}`
        this.isAuthenticated.set(true)

        return true
    }

    checkAuthentication() {
        if (this.bearerToken !== null) {
            this.decodedToken = jwtDecode(
                this.bearerToken.substring(7, this.bearerToken.length - 1)
            )

            if (!this.tokenIsExpired(this.decodedToken.exp)) {
                return this.isAuthenticated.set(true)
            }
        }
    }

    private tokenIsExpired(expireIn: number | undefined) {
        const now = (Date.now() / 1000).toFixed()
        if (expireIn === undefined) return true
        return expireIn > parseInt(now) ? false : true

    }
}
