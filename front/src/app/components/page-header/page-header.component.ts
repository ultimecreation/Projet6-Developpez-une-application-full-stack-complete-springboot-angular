import { Location } from '@angular/common';
import { Component, inject, input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-page-header',
    imports: [],
    templateUrl: './page-header.component.html',
    styleUrl: './page-header.component.scss'
})
export class PageHeaderComponent {

    title = input<string>('')

    private location = inject(Location)

    handleGoBack() {
        this.location.back()
    }
}
