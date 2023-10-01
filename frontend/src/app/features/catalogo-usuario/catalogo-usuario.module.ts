import { CommonModule } from '@angular/common'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { NgIf } from '@angular/common'
import { NgModule } from '@angular/core'
import { RouterModule } from '@angular/router'

import { SharedModule } from 'src/app/shared/shared.module'

import { AgregarLibroComponent } from './components/agregar-libro/agregar-libro.component'
import { HomePageComponent } from './pages/home-page/home-page.component'
import { MiCatalogoPageComponent } from './pages/mi-catalogo-page/mi-catalogo-page.component'

import { TextFieldModule } from '@angular/cdk/text-field'

import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatIconModule } from '@angular/material/icon'
import { MatInputModule } from '@angular/material/input'
import { MatListModule } from '@angular/material/list'
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatToolbarModule } from '@angular/material/toolbar'

@NgModule({
  declarations: [HomePageComponent, MiCatalogoPageComponent, AgregarLibroComponent],
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
  ],
  exports: [HomePageComponent, MiCatalogoPageComponent, AgregarLibroComponent],
})
export class CatalogoUsuarioModule {}
