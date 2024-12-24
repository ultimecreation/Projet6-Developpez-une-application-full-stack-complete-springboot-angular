import { Routes } from '@angular/router';

import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { authGuard } from './guards/auth.guard';
import { ArticleAddComponent } from './pages/articles/article-add/article-add.component';
import { ArticleDetailsComponent } from './pages/articles/article-details/article-details.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'articles', component: ArticlesComponent, canActivate: [authGuard] },
    { path: 'articles/add', component: ArticleAddComponent, canActivate: [authGuard] },
    { path: 'articles/:id', component: ArticleDetailsComponent, canActivate: [authGuard] },
    { path: 'themes', component: ThemesComponent, canActivate: [authGuard] },
    { path: 'profile', component: ProfileComponent, canActivate: [authGuard] },
];
