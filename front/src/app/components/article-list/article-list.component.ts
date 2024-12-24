import { Component, input } from '@angular/core';
import { ArticleInterface } from '../../interfaces/ArticleInterface';
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-article-list',
    imports: [RouterLink],
    templateUrl: './article-list.component.html',
    styleUrl: './article-list.component.scss'
})
export class ArticleListComponent {
    articles = input<ArticleInterface[] | undefined>([])
}
