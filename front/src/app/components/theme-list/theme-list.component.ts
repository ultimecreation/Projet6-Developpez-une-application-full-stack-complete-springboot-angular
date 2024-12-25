import { Component, input, OnInit, output } from '@angular/core';
import { ThemeInterface } from '../../interfaces/ThemeInterface';

@Component({
    selector: 'app-theme-list',
    imports: [],
    templateUrl: './theme-list.component.html',
    styleUrl: './theme-list.component.scss'
})
export class ThemeListComponent {
    themes = input<ThemeInterface[] | undefined>([])
    btnText = input("S'abonner")
    handleSubmitEvent = output<{ themeId: string }>()
}
