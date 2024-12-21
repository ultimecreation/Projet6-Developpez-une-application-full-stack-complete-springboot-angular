import { Routes } from '@angular/router';

import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { HomeComponent } from './pages/home/home.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'articles', component: ArticlesComponent, canActivate: [authGuard] },
    { path: 'themes', component: ThemesComponent },
    { path: 'profile', component: ProfileComponent, canActivate: [authGuard] },
];
