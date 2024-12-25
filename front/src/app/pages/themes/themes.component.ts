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

    private themeService = inject(ThemeService)
    private destroyRef = inject(DestroyRef)

    themes: ThemeInterface[] = []

    ngOnInit(): void {
        const getAllThemesSubscription = this.themeService.getAllThemes().subscribe({
            next: (data) => this.themes = [...data.topics],
            error: (err) => {
                console.log(err)
            }
        })
        this.destroyRef.onDestroy(() => getAllThemesSubscription.unsubscribe())
    }


    handleSubmitForChild($event: { themeId: string; }) {
        const themeId = $event.themeId
        const addUserThemeSubscription = this.themeService.addUserThemeSubscription(themeId).subscribe()
        this.destroyRef.onDestroy(() => addUserThemeSubscription.unsubscribe())
    }


    private fetchThemes = () => {

    }
}
