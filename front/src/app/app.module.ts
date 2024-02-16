
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './features/auth/components/login/login.component';
import { HeaderComponent } from './header/header.component';
import { MatIconModule } from '@angular/material/icon';
import { NgModule } from '@angular/core';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { AuthModule } from './features/auth/auth.module';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderNavComponent } from './header-nav/header-nav.component';
import { TopicComponent } from './topic/topic.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { MeComponent } from './me/me.component'; // Importa MatToolbarModule
import { ProfileComponent } from './profile/profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TopicsCardComponent } from './topic-card/topic-card.component';



@NgModule({
  declarations: [AppComponent, HomeComponent, ProfileComponent, HeaderNavComponent, TopicComponent, NotFoundComponent, MeComponent, TopicsCardComponent],
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

  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
