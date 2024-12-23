import { Component, input } from '@angular/core';
import { ArticleInterface } from '../../interfaces/ArticleInterface';

@Component({
    selector: 'app-article-list',
    imports: [],
    templateUrl: './article-list.component.html',
    styleUrl: './article-list.component.scss'
})
export class ArticleListComponent {
    articles = input<ArticleInterface[] | undefined>([])
}
