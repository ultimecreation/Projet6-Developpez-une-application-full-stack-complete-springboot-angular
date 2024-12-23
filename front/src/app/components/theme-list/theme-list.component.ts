import { Component, input, OnInit } from '@angular/core';
import { ThemeInterface } from '../../interfaces/ThemeInterface';

@Component({
    selector: 'app-theme-list',
    imports: [],
    templateUrl: './theme-list.component.html',
    styleUrl: './theme-list.component.scss'
})
export class ThemeListComponent {
    themes = input<ThemeInterface[] | undefined>([])
}
