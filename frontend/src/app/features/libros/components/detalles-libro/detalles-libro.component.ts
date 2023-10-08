import { Component, OnInit } from '@angular/core'
import { DefaultService, DetallesLibro } from 'src/generated/openapi'

@Component({
  selector: 'app-detalles-libro',
  templateUrl: './detalles-libro.component.html',
  styleUrls: ['./detalles-libro.component.sass'],
})
export class DetallesLibroComponent implements OnInit {
  public infoLibro!: DetallesLibro

  constructor(private serviceApi: DefaultService) {}

  // eslint-disable-next-line @angular-eslint/use-lifecycle-interface
  ngOnInit(): void {
    this.recuperaInfoLibro()
  }

  recuperaInfoLibro() {
    this.serviceApi.getLibro('dfadfadf').subscribe(libro => {
      this.infoLibro = libro
    })
  }
}
