import { Component, DestroyRef, inject, Input, input, OnInit } from '@angular/core';
import { NavbarComponent } from "../../../components/navbar/navbar.component";
import { Location } from '@angular/common';
import { ArticleService } from '../../../services/article.service';
import { ArticleInterface } from '../../../interfaces/ArticleInterface';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-article-details',
    imports: [NavbarComponent],
    templateUrl: './article-details.component.html',
    styleUrl: './article-details.component.scss'
})
export class ArticleDetailsComponent implements OnInit {


    private location = inject(Location)
    private articleService = inject(ArticleService)
    private activatedRoute = inject(ActivatedRoute)
    private destroyRef = inject(DestroyRef)

    article!: ArticleInterface
    ngOnInit(): void {
        const articleId = this.activatedRoute.snapshot.paramMap.get('id')!
        const getArticleByIdSubscription = this.articleService.getPostById(articleId).subscribe({
            next: (data) => {
                this.article = { ...data }
                console.log(data)
            },
            error: err => console.log(err)
        })
        this.destroyRef.onDestroy(() => getArticleByIdSubscription.unsubscribe())

    }
    handleGoBack() {
        this.location.back()
    }

}


function RouterInput(): (target: ArticleDetailsComponent, propertyKey: "testId") => void {
    throw new Error('Function not implemented.');
}

