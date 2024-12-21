import { Location } from '@angular/common';
import { Component, inject, input } from '@angular/core';
import { Router } from '@angular/router';
import { NavbarComponent } from "../navbar/navbar.component";

@Component({
    selector: 'app-page-header',
    imports: [NavbarComponent],
    templateUrl: './page-header.component.html',
    styleUrl: './page-header.component.scss'
})
export class PageHeaderComponent {

    title = input<string>('')
    invisible = input<boolean>(false)

    private location = inject(Location)

    handleGoBack() {
        this.location.back()
    }
}
