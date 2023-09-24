import { Injectable } from '@angular/core'
import { BehaviorSubject } from 'rxjs'

@Injectable({
  providedIn: 'root',
})
export class ServicioUsuario {
  public readonly id: BehaviorSubject<number>

  constructor() {
    const idInicial = Number(localStorage.getItem('idUsuario')) || 0
    this.id = new BehaviorSubject(idInicial)

    this.id.subscribe(id => {
      localStorage.setItem('idUsuario', String(id))
    })
  }

  setId(userId: number) {
    this.id.next(userId)
  }
}
