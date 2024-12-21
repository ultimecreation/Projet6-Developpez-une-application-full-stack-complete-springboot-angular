import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UpdateRequestInterface } from '../../interfaces/UpdateProfileRequestInterface';


@Component({
    selector: 'app-profile-form',
    imports: [ReactiveFormsModule],
    templateUrl: './profile-form.component.html',
    styleUrl: './profile-form.component.scss'
})
export class ProfileFormComponent implements OnInit {
    private authService = inject(AuthService)
    private router = inject(Router)
    userInfos: any = null
    errorMsg = ""
    errors: any = {}
    form: any = null

    ngOnInit(): void {
        this.authService.getUserInfos().subscribe({
            next: (data: any) => {
                this.userInfos = data

                this.form = new FormGroup({
                    username: new FormControl(this.userInfos.username, [Validators.required]),
                    email: new FormControl(this.userInfos.email, [Validators.required, Validators.email]),
                    password: new FormControl('', [Validators.required])
                })


            }
        })


    }

    handleSubmit($event: Event) {
        this.errors = {}
        this.errorMsg = ""
        $event.preventDefault()
        const { username, email, password } = this.form.value
        const updateProfileRequest: UpdateRequestInterface = { id: this.userInfos.id, username, email, password }
        this.authService.updateProfile(updateProfileRequest).subscribe({
            next: (data: any) => {
                if (data.jwtToken) {
                    const success = this.authService.saveUserToLocalStorage(data.jwtToken)
                    if (success) window.location.reload()
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

    handleLogout() {
        this.authService.logout()
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
