import { NgModule } from '@angular/core'
import { CommonModule, NgIf } from '@angular/common'
import { RouterModule } from '@angular/router'
import { MostrarLibrosComponent } from './pages/mostrar-libros/mostrar-libros.component'
import { DetallesLibroComponent } from './components/detalles-libro/detalles-libro.component'

import { MatFormFieldModule } from '@angular/material/form-field'
import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatIconModule } from '@angular/material/icon'
import { MatInputModule } from '@angular/material/input'
import { MatListModule } from '@angular/material/list'
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatToolbarModule } from '@angular/material/toolbar'
import { FormsModule } from '@angular/forms'
import { SharedModule } from 'src/app/shared/shared.module'

import { HttpClientModule, HttpHeaders } from '@angular/common/http'

@NgModule({
  declarations: [MostrarLibrosComponent, DetallesLibroComponent],
  imports: [
    CommonModule,
    RouterModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule,
    FormsModule,
    NgIf,
    SharedModule,
    HttpClientModule,
    HttpHeaders,
  ],
  exports: [MostrarLibrosComponent, DetallesLibroComponent],
})
export class librosModule {}
