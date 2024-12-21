import { Component } from '@angular/core';
import { FeedListComponent } from "../feed-list/feed-list.component";

@Component({
    selector: 'app-home-authenticated',
    imports: [FeedListComponent],
    templateUrl: './home-authenticated.component.html',
    styleUrl: './home-authenticated.component.scss'
})
export class HomeAuthenticatedComponent {

}
