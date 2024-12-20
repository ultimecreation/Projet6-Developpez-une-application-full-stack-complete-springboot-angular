import { HttpClient } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';
import { NavbarComponent } from "./components/navbar/navbar.component";

@Component({
    selector: 'app-root',
    imports: [RouterOutlet, NavbarComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {

    title = 'front';

    authService = inject(AuthService)
    ngOnInit(): void {
        this.authService.checkAuthentication()
    }
}
