import { Component, DestroyRef, inject, signal } from '@angular/core';
import { NavbarComponent } from "../../../components/navbar/navbar.component";
import { Location } from '@angular/common';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { ArticleAddRequestInterface } from '../../../interfaces/ArticleAddRequestInterface';
import { ArticleService } from '../../../services/article.service';
import { ThemeService } from '../../../services/theme.service';
import { ThemeInterface } from '../../../interfaces/ThemeInterface';

@Component({
    selector: 'app-article-add',
    imports: [NavbarComponent, ReactiveFormsModule],
    templateUrl: './article-add.component.html',
    styleUrl: './article-add.component.scss'
})
export class ArticleAddComponent {

    private location = inject(Location)
    private articleService = inject(ArticleService)
    private router = inject(Router)
    private destroyRef = inject(DestroyRef)

    themeService = inject(ThemeService)
    errorMsg = ""
    errors: any = {}
    form: any = null
    themes = signal<ThemeInterface[]>([])

    ngOnInit(): void {
        const getAllThemesSubscription = this.themeService.getAllThemes().subscribe({
            next: (data) => this.themes.set([...data.topics]),
            error: (err) => console.log(err)
        })
        this.destroyRef.onDestroy(() => getAllThemesSubscription.unsubscribe())

        this.form = new FormGroup({
            theme: new FormControl('', [Validators.required]),
            title: new FormControl('', [Validators.required]),
            content: new FormControl('', [Validators.required])
        })
    }
    handleGoBack() {
        this.location.back()
    }

    handleSubmit($event: Event) {
        $event.preventDefault()
        this.errors = {}
        this.errorMsg = ""
        $event.preventDefault()
        const { theme, title, content } = this.form.value
        const articleAddRequest: ArticleAddRequestInterface = { topic_id: theme, title, content }

        this.articleService.save(articleAddRequest).subscribe({
            next: () => {
                this.form.reset()
                this.router.navigateByUrl("/articles")
            },
            error: errorData => {
                this.errors = errorData.error.errors
                if (errorData.error.message) {
                    this.errorMsg = errorData.error.message
                }
            }
        })
    }

    isDisabled() {
        return (this.form.controls.title.invalid || this.form.controls.content.invalid)
    }

    get theme() {
        return this.form.get('theme')
    }
    get title() {
        return this.form.get('title')
    }

    get content() {
        return this.form.get('content')
    }

    get themeIsInvalid() {
        return this.theme?.invalid && (this.theme?.dirty || this.theme?.touched)
    }

    get titleIsInvalid() {
        return this.title?.invalid && (this.title?.dirty || this.title?.touched)
    }
    get contentIsInvalid() {
        return this.content?.invalid && (this.content?.dirty || this.content?.touched)
    }

}
