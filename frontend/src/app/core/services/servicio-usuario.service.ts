import { Injectable, WritableSignal, effect, signal } from '@angular/core'

@Injectable({
  providedIn: 'root',
})
export class ServicioUsuario {
  public readonly id: WritableSignal<number>
  public readonly token: WritableSignal<string>

  constructor() {
    const idInicial = Number(localStorage.getItem('idUsuario')) || 0
    this.id = signal(idInicial)

    const tokenInicial = localStorage.getItem('token') || ''
    this.token = signal(tokenInicial)

    effect(() => {
      localStorage.setItem('idUsuario', String(this.id()))
    })
    effect(() => {
      localStorage.setItem('token', String(this.token()))
    })
  }

  logout() {
    this.id.set(0)
    this.token.set('')
  }
}
