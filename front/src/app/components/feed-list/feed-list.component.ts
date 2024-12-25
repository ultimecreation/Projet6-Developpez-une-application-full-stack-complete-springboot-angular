import { Component, DestroyRef, inject, signal } from '@angular/core';
import { Subscription } from 'rxjs';
import { ArticleInterface } from '../../interfaces/ArticleInterface';
import { ArticleService } from '../../services/article.service';
import { ArticleListComponent } from "../article-list/article-list.component";
import { RouterLink } from '@angular/router';

@Component({
    selector: 'app-feed-list',
    imports: [ArticleListComponent, RouterLink],
    templateUrl: './feed-list.component.html',
    styleUrl: './feed-list.component.scss'
})
export class FeedListComponent {

    private articleService = inject(ArticleService)
    private destroyRef = inject(DestroyRef)

    articles: ArticleInterface[] = []
    filteredArticles: ArticleInterface[] = []
    sortByDesc = signal<boolean>(true)
    ngOnInit(): void {
        let subscription: Subscription
        subscription = this.articleService.getUserFeed().subscribe({
            next: (data) => {
                this.articles = [...data.posts]
                this.filteredArticles = this.articles
            },
            error: (err) => {
                console.log(err)
            }
        })
        this.destroyRef.onDestroy(() => subscription.unsubscribe())
    }

    handleSortToggle(): void {
        this.sortByDesc.set(!this.sortByDesc())
        this.filteredArticles.sort((a: ArticleInterface, b: ArticleInterface): number => {
            const aDate = new Date(a.created_at).getTime()
            const bDate = new Date(b.created_at).getTime()

            return this.sortByDesc() === false ? aDate - bDate : bDate - aDate

        });
    }

}
