
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './features/auth/components/login/login.component';
import { HeaderComponent } from './components-header/header/header.component';
import { MatIconModule } from '@angular/material/icon';
import { NgModule } from '@angular/core';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatCardHeader, MatCardModule } from '@angular/material/card';
import { AuthModule } from './features/auth/auth.module';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderNavComponent } from './components-header/header-nav/header-nav.component';
import { TopicComponent } from './components-app/topic/topic.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { MeComponent } from './components-app/me/me.component'; // Importa MatToolbarModule
import { ProfileComponent } from './components-app/profile/profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TopicsCardComponent } from './components-app/topic-card/topic-card.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { CreatePostComponent } from './components-app/create-post/create-post.component';
import { MatOption, MatOptionModule } from '@angular/material/core';
import { PostComponent } from './components-app/post/post.component';
import { CreateTopicComponent } from './components-app/create-topic/create-topic.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';

@NgModule({
  declarations: [AppComponent, HomeComponent, ProfileComponent, HeaderNavComponent, TopicComponent, NotFoundComponent, MeComponent, TopicsCardComponent, PostComponent, CreateTopicComponent,CreatePostComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatCardModule,
    AuthModule,
    MatToolbarModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatOptionModule,
    MatSnackBarModule,
    CommonModule,
    ReactiveFormsModule,
    MatSelectModule, 
    MatInputModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
