import { Component, OnInit } from '@angular/core'
import { DefaultService, PaginaLibros } from 'src/generated/openapi'

@Component({
  selector: 'app-mostrar-libros',
  templateUrl: './mostrar-libros.component.html',
  styleUrls: ['./mostrar-libros.component.sass'],
})
export class MostrarLibrosComponent implements OnInit {
  public libros!: PaginaLibros

  constructor(private serviceApi: DefaultService) {}

  // eslint-disable-next-line @angular-eslint/use-lifecycle-interface
  ngOnInit(): void {
    this.recuperaLibros()
  }

  recuperaLibros() {
    //this.serviceApi.getLibros(0).subscribe()
    this.serviceApi.getLibros().subscribe(total => {
      this.libros = total
    })
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  sendValues(isbn: string) {
    // eslint-disable-next-line no-console
    console.log('estoy aqui: ', isbn)
  }
}
