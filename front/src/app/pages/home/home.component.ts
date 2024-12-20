import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { HomeAuthenticatedComponent } from "../../components/home-authenticated/home-authenticated.component";
import { HomeUnauthenticatedComponent } from "../../components/home-unauthenticated/home-unauthenticated.component";
import { NavbarComponent } from "../../components/navbar/navbar.component";

@Component({
    selector: 'app-home',
    imports: [HomeAuthenticatedComponent, HomeUnauthenticatedComponent, NavbarComponent],
    templateUrl: './home.component.html',
    styleUrl: './home.component.scss'
})
export class HomeComponent {
    authService = inject(AuthService)


}
