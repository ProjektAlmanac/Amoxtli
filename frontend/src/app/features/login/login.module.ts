import { CommonModule } from '@angular/common'
import { NgModule } from '@angular/core'

import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatIconModule } from '@angular/material/icon'
import { MatInputModule } from '@angular/material/input'
import { MatSnackBarModule } from '@angular/material/snack-bar'
import { RouterModule } from '@angular/router'

import { LoginFormComponent } from './components/login-form/login-form.component'
import { LoginPageComponent } from './pages/login-page/login-page.component'

@NgModule({
  declarations: [LoginPageComponent, LoginFormComponent],
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSnackBarModule,
    ReactiveFormsModule,
    RouterModule,
  ],
  exports: [LoginPageComponent],
})
export class LoginModule {}
