import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { LoginRequestInterface } from '../../interfaces/LoginRequestInterface';
import { Router } from '@angular/router';
import { PageHeaderComponent } from "../../components/page-header/page-header.component";

@Component({
    selector: 'app-login',
    imports: [ReactiveFormsModule, PageHeaderComponent],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
    private authService = inject(AuthService)
    private router = inject(Router)
    errorMsg = ""
    errors: any = {}
    form: any = null

    ngOnInit(): void {
        this.form = new FormGroup({
            email: new FormControl('', [Validators.required]),
            password: new FormControl('', [Validators.required])
        })
    }

    handleSubmit($event: Event) {
        this.errors = {}
        this.errorMsg = ""
        $event.preventDefault()
        const { email, password } = this.form.value
        const loginRequest: LoginRequestInterface = { email, password }
        this.authService.login(loginRequest).subscribe({
            next: (data: any) => {
                if (data.jwtToken) {
                    const success = this.authService.saveUserToLocalStorage(data.jwtToken)
                    if (success) this.router.navigateByUrl('/profile')
                }

            },
            error: errorData => {
                this.errors = errorData.error.errors
                if (errorData.error.message) {
                    this.errorMsg = errorData.error.message
                }
            }
        })
    }

    isDisabled() {
        return (this.form.controls.email.invalid || this.form.controls.password.invalid)
    }

    get email() {
        return this.form.get('email')
    }

    get password() {
        return this.form.get('password')
    }

    get emailIsInvalid() {
        return this.email?.invalid && (this.email?.dirty || this.email?.touched)
    }
    get passswordIsInvalid() {
        return this.password?.invalid && (this.password?.dirty || this.password?.touched)
    }
}
