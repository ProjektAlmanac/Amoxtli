import { Component, Input } from '@angular/core'
import { Dueno } from 'src/generated/openapi'

@Component({
  selector: 'app-duenos',
  templateUrl: './duenos.component.html',
  styleUrls: ['./duenos.component.sass'],
})
export class DuenosComponent {
  @Input()
  public duenos!: Dueno[]
}
