import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'

import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatIconModule } from '@angular/material/icon'
import { MatListModule } from '@angular/material/list'
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatToolbarModule } from '@angular/material/toolbar'
import { MatInputModule } from '@angular/material/input'
import { RouterModule } from '@angular/router'
import { NgIf } from '@angular/common'
import { FormsModule } from '@angular/forms'
import { MatFormFieldModule } from '@angular/material/form-field'

import { HomePageComponent } from './pages/home-page/home-page.component'
import { MiCatalogoPageComponent } from './pages/mi-catalogo-page/mi-catalogo-page.component'
import { AgregarLibroComponent } from './components/agregar-libro/agregar-libro.component'
import { SharedModule } from 'src/app/shared/shared.module'

@NgModule({
  declarations: [HomePageComponent, MiCatalogoPageComponent, AgregarLibroComponent],
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule,
    MatInputModule,
    RouterModule,
    MatFormFieldModule,
    FormsModule,
    NgIf,
    SharedModule,
  ],
  exports: [HomePageComponent, MiCatalogoPageComponent, AgregarLibroComponent],
})
export class UserModule {}
