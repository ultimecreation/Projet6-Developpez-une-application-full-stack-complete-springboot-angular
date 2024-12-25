import { Component, DestroyRef, inject, OnDestroy, OnInit } from '@angular/core';
import { ProfileFormComponent } from "../../components/profile-form/profile-form.component";
import { PageHeaderComponent } from "../../components/page-header/page-header.component";
import { NavbarComponent } from "../../components/navbar/navbar.component";
import { Subscription } from 'rxjs';
import { ThemeInterface } from '../../interfaces/ThemeInterface';
import { ThemeService } from '../../services/theme.service';
import { ThemeListComponent } from "../../components/theme-list/theme-list.component";

@Component({
    selector: 'app-profile',
    imports: [ProfileFormComponent, PageHeaderComponent, NavbarComponent, ThemeListComponent],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

    private themeService = inject(ThemeService)
    private destroyRef = inject(DestroyRef)
    themes: ThemeInterface[] = []

    ngOnInit(): void {
        this.fetchThemes()
    }

    handleSubmitForChild($event: { themeId: string; }) {
        const removeUserThemeSubscriptionSubscription = this.themeService.removeUserThemeSubscription($event.themeId).subscribe({
            next: () => {
                this.fetchThemes()
            },
            error: err => console.log(err)
        })
        this.destroyRef.onDestroy(() => removeUserThemeSubscriptionSubscription.unsubscribe())
    }

    private fetchThemes = () => {
        const getSubscribedThemesSubscription = this.themeService.getSubscribedThemes().subscribe({
            next: (data) => this.themes = [...data.topics],
            error: (err) => {
                console.log(err)
            }
        })
        this.destroyRef.onDestroy(() => getSubscribedThemesSubscription.unsubscribe())
    }
}
