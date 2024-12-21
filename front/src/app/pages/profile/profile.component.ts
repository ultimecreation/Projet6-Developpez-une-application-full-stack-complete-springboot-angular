import { Component } from '@angular/core';
import { ProfileFormComponent } from "../../components/profile-form/profile-form.component";
import { PageHeaderComponent } from "../../components/page-header/page-header.component";

@Component({
    selector: 'app-profile',
    imports: [ProfileFormComponent, PageHeaderComponent],
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.scss'
})
export class ProfileComponent {

}
