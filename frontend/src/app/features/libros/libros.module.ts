import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { RouterModule } from '@angular/router'
import { MostrarLibrosComponent } from './pages/mostrar-libros/mostrar-libros.component'
import { DetallesLibroComponent } from './components/detalles-libro/detalles-libro.component'

@NgModule({
  declarations: [MostrarLibrosComponent, DetallesLibroComponent],
  imports: [CommonModule, RouterModule],
  exports: [MostrarLibrosComponent, DetallesLibroComponent],
})
export class librosModule {}
