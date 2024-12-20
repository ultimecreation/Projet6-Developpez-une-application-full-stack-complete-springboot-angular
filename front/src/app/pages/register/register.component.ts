import { Component, inject } from '@angular/core';
import { PageHeaderComponent } from "../../components/page-header/page-header.component";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegisterRequestInterface } from '../../interfaces/RegisterRequestInterface';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-register',
    imports: [PageHeaderComponent, ReactiveFormsModule],
    templateUrl: './register.component.html',
    styleUrl: './register.component.scss'
})
export class RegisterComponent {

    private authService = inject(AuthService)
    private router = inject(Router)
    errorMsg = ""
    errors: any = {}
    form: any = null

    ngOnInit(): void {
        this.form = new FormGroup({
            username: new FormControl('', [Validators.required]),
            email: new FormControl('', [Validators.required, Validators.email]),
            password: new FormControl('', [Validators.required])
        })
    }

    handleSubmit($event: Event) {
        this.errors = {}
        this.errorMsg = ""
        $event.preventDefault()
        const { username, email, password } = this.form.value
        const registerRequest: RegisterRequestInterface = { username, email, password }
        this.authService.register(registerRequest).subscribe({
            next: (data: any) => {
                if (data.jwtToken) {
                    const success = this.authService.saveUserToLocalStorage(data.jwtToken)
                    if (success) this.router.navigateByUrl('/')
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

    get username() {
        return this.form.get('username')
    }
    get email() {
        return this.form.get('email')
    }

    get password() {
        return this.form.get('password')
    }

    get usernameIsInvalid() {
        return this.username?.invalid && (this.username?.dirty || this.username?.touched)
    }

    get emailIsInvalid() {
        return this.email?.invalid && (this.email?.dirty || this.email?.touched)
    }
    get passswordIsInvalid() {
        return this.password?.invalid && (this.password?.dirty || this.password?.touched)
    }
}
