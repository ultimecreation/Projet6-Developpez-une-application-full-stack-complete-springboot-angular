import { Location } from '@angular/common';
import { Component, inject, input } from '@angular/core';

@Component({
    selector: 'app-page-header',
    imports: [],
    templateUrl: './page-header.component.html',
    styleUrl: './page-header.component.scss'
})
export class PageHeaderComponent {

    title = input<string>('')
    goBackBtnInvisible = input<boolean>(false)

    private location = inject(Location)

    handleGoBack() {
        this.location.back()
    }
}
