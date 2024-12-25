import { Component, DestroyRef, inject, input, output } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { CommentService } from '../../services/comment.service';
import { CommentRequestInterface } from '../../interfaces/CommentRequestInterface';

@Component({
    selector: 'app-comment-form',
    imports: [ReactiveFormsModule],
    templateUrl: './comment-form.component.html',
    styleUrl: './comment-form.component.scss'
})
export class CommentFormComponent {
    private commentService = inject(CommentService)
    private destroyRef = inject(DestroyRef)

    errorMsg = ""
    errors: any = {}
    articleId = input<string>('')
    refetchComments = output()


    form = new FormGroup({
        content: new FormControl('', [Validators.required])
    })

    handleSubmit($event: Event) {
        $event.preventDefault()
        this.errors = {}
        this.errorMsg = ""

        const { content } = this.form.value
        const createCommentRequest: CommentRequestInterface = { post_id: this.articleId(), content: content! }

        this.saveComment(createCommentRequest);
        this.refetchComments.emit()
        this.form.reset()
    }

    private saveComment(createCommentRequest: CommentRequestInterface) {
        const saveCommentSubscription = this.commentService.saveComment(createCommentRequest).subscribe({
            next: (data: any) => {
                console.log(data);
            },
            error: errorData => {
                console.log(errorData);
                this.errors = errorData.error.errors;
                if (errorData.error.message) {
                    this.errorMsg = errorData.error.message;
                }
            }
        });
        this.destroyRef.onDestroy(() => saveCommentSubscription.unsubscribe());
    }

    isDisabled() {
        return this.form.controls.content.invalid
    }

    get content() {
        return this.form.get('content')
    }

    get contentIsInvalid() {
        return this.content?.invalid && (this.content?.dirty || this.content?.touched)
    }
}
