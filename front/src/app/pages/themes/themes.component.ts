import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { NavbarComponent } from "../../components/navbar/navbar.component";
import { ThemeService } from '../../services/theme.service';
import { ThemeInterface } from '../../interfaces/ThemeInterface';
import { ThemeListComponent } from "../../components/theme-list/theme-list.component";
import { AuthService } from '../../services/auth.service';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-themes',
    imports: [NavbarComponent, ThemeListComponent],
    templateUrl: './themes.component.html',
    styleUrl: './themes.component.scss'
})
export class ThemesComponent implements OnInit {

    private authService = inject(AuthService)
    private themeService = inject(ThemeService)
    private destroyRef = inject(DestroyRef)

    themes: ThemeInterface[] = []

    ngOnInit(): void {
        let subscription: Subscription
        if (this.authService.isAuthenticated()) {
            subscription = this.themeService.getUnsubscribedThemes().subscribe({
                next: (data) => this.themes = [...data.topics],
                error: (err) => {
                    console.log(err)
                }
            })
        } else {
            subscription = this.themeService.getAllThemes().subscribe({
                next: (data) => this.themes = [...data.topics],
                error: (err) => {
                    console.log(err)
                }
            })
        }

        this.destroyRef.onDestroy(() => subscription.unsubscribe())
    }

}
