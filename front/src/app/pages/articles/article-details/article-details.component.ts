import { ChangeDetectorRef, Component, DestroyRef, inject, Input, input, OnChanges, OnInit, signal, SimpleChanges } from '@angular/core';
import { NavbarComponent } from "../../../components/navbar/navbar.component";
import { Location } from '@angular/common';
import { ArticleService } from '../../../services/article.service';
import { ArticleInterface } from '../../../interfaces/ArticleInterface';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentService } from '../../../services/comment.service';
import { CommentInterface } from '../../../interfaces/CommentInterface';
import { CommentFormComponent } from "../../../components/comment-form/comment-form.component";

@Component({
    selector: 'app-article-details',
    imports: [NavbarComponent, CommentFormComponent],
    templateUrl: './article-details.component.html',
    styleUrl: './article-details.component.scss'
})
export class ArticleDetailsComponent implements OnInit, OnChanges {

    private location = inject(Location)
    private articleService = inject(ArticleService)
    private commentService = inject(CommentService)
    private activatedRoute = inject(ActivatedRoute)
    private destroyRef = inject(DestroyRef)
    private router = inject(Router)

    article = signal<Partial<ArticleInterface>>({})
    comments = signal<CommentInterface[]>([])
    // comments: CommentInterface[] = []
    articleId = signal<string>('')

    ngOnInit(): void {
        this.articleId.set(this.activatedRoute.snapshot.paramMap.get('id')!)
        this.fetchArticle(this.articleId());
        this.fetchcomments(this.articleId());
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.fetchArticle(this.articleId());
    }

    private fetchArticle(articleId: string) {
        const getArticleByIdSubscription = this.articleService.getPostById(articleId).subscribe({
            next: (data) => this.article.set({ ...data }),
            error: err => console.log(err)
        });
        this.destroyRef.onDestroy(() => getArticleByIdSubscription.unsubscribe());
    }

    fetchcomments(articleId: string) {
        const getAllCommentsByArticleIdSubscription = this.commentService.getAllCommentsByArticleId(articleId)
            .subscribe({
                next: (data) => this.comments.set([...data.comments]),
                error: err => console.log(err)
            });
        this.destroyRef.onDestroy(() => getAllCommentsByArticleIdSubscription.unsubscribe());
    }

    handleGoBack() {
        this.location.back()
    }

    handleRefetchComments() {
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigateByUrl(`articles/${this.articleId()}`);
        });

    }
}