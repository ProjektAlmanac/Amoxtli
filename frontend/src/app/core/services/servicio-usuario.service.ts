import { Injectable, WritableSignal, effect, signal } from '@angular/core'

@Injectable({
  providedIn: 'root',
})
export class ServicioUsuario {
  public readonly id: WritableSignal<number>

  constructor() {
    const idInicial = Number(localStorage.getItem('idUsuario')) || 0
    this.id = signal(idInicial)

    effect(() => {
      localStorage.setItem('idUsuario', String(this.id()))
    })
  }
}
