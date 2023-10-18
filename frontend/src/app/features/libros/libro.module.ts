import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { NgIf } from '@angular/common'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { RouterModule } from '@angular/router'

import { SharedModule } from 'src/app/shared/shared.module'

import { TextFieldModule } from '@angular/cdk/text-field'

import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatIconModule } from '@angular/material/icon'
import { MatInputModule } from '@angular/material/input'
import { MatListModule } from '@angular/material/list'
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatChipsModule } from '@angular/material/chips'

import { LibroPageComponent } from './pages/libro-page/libro-page.component'
import { DuenosComponent } from './components/duenos/duenos.component'
import { LibroComponent } from './components/libro/libro.component'
import { InvolucradosComponent } from './components/involucrados/involucrados.component'

@NgModule({
  declarations: [LibroPageComponent, DuenosComponent, LibroComponent, InvolucradosComponent],
  imports: [
    CommonModule,
    FormsModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule,
    NgIf,
    ReactiveFormsModule,
    RouterModule,
    SharedModule,
    TextFieldModule,
    MatChipsModule,
  ],
  exports: [LibroPageComponent, LibroComponent],
})
export class LibroModule {}
