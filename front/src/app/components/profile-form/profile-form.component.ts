import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { UserService } from '../../services/user.service';
import { UpdateProfileRequestInterface } from '../../interfaces/UpdateProfileRequestInterface';


@Component({
    selector: 'app-profile-form',
    imports: [ReactiveFormsModule],
    templateUrl: './profile-form.component.html',
    styleUrl: './profile-form.component.scss'
})
export class ProfileFormComponent implements OnInit {

    private userService = inject(UserService)
    private authService = inject(AuthService)
    private destroyRef = inject(DestroyRef)
    subscription!: Subscription
    userInfos: any = null
    errorMsg = ""
    errors: any = {}
    form: any = null

    ngOnInit(): void {

        const subscription = this.userService.getUserInfos().subscribe({
            next: (data: any) => {
                this.userInfos = data

                this.form = new FormGroup({
                    username: new FormControl(this.userInfos.username, [Validators.required]),
                    email: new FormControl(this.userInfos.email, [Validators.required, Validators.email]),
                    password: new FormControl('', [Validators.required])
                })
            }
        })
        this.destroyRef.onDestroy(() => {
            subscription.unsubscribe()
        })
    }

    handleSubmit($event: Event) {
        $event.preventDefault()
        this.errors = {}
        this.errorMsg = ""
        $event.preventDefault()
        const { username, email, password } = this.form.value
        const updateProfileRequest: UpdateProfileRequestInterface = { id: this.userInfos.id, username, email, password }
        this.userService.updateProfile(updateProfileRequest).subscribe({
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
