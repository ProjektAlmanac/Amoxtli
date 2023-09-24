import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'

import { SignInPageComponent } from './pages/sign-up-page/sign-up-page.component'
import { SignInFormComponent } from './components/sign-up-form/sign-up-form.component'
import { VerifyEmailPageComponent } from './pages/verify-email-page/verify-email-page.component'
import { VerifyEmailFormComponent } from './components/verify-email-form/verify-email-form.component'

import { MatInputModule } from '@angular/material/input'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatSnackBarModule } from '@angular/material/snack-bar'
import { MatIconModule } from '@angular/material/icon'

import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { RouterModule } from '@angular/router'
import { WelcomePageComponent } from './pages/welcome-page/welcome-page.component'

@NgModule({
  declarations: [
    SignInPageComponent,
    SignInFormComponent,
    VerifyEmailPageComponent,
    VerifyEmailFormComponent,
    WelcomePageComponent,
  ],
  imports: [
    CommonModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    RouterModule,
    MatSnackBarModule,
    MatIconModule,
  ],
  exports: [SignInPageComponent, VerifyEmailPageComponent, WelcomePageComponent],
})
export class SignInModule {}
