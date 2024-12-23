import { Component, DestroyRef, inject } from '@angular/core';
import { NavbarComponent } from "../../components/navbar/navbar.component";
import { ArticleInterface } from '../../interfaces/ArticleInterface';
import { Subscription } from 'rxjs';
import { ArticleService } from '../../services/article.service';
import { ArticleListComponent } from "../../components/article-list/article-list.component";

@Component({
    selector: 'app-articles',
    imports: [NavbarComponent, ArticleListComponent],
    templateUrl: './articles.component.html',
    styleUrl: './articles.component.scss'
})
export class ArticlesComponent {

    private articleService = inject(ArticleService)
    private destroyRef = inject(DestroyRef)

    articles: ArticleInterface[] = []

    ngOnInit(): void {
        let subscription: Subscription
        subscription = this.articleService.getAllPosts().subscribe({
            next: (data) => {
                this.articles = [...data.posts]
                console.log(this.articles)
            },
            error: (err) => {
                console.log(err)
            }
        })
        this.destroyRef.onDestroy(() => subscription.unsubscribe())
    }
}
