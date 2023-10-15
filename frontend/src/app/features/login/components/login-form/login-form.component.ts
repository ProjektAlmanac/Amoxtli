import { Component, EventEmitter, Input, Output } from '@angular/core'
import { FormBuilder, Validators } from '@angular/forms'
import { Credenciales } from 'src/generated/openapi'

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.sass'],
})
export class LoginFormComponent {
  @Input()
  mensajeError = ''

  @Output()
  formSubmit = new EventEmitter<Credenciales>()

  readonly PASSWORD_LENGTH = 8
  public mostrarContrasena = false

  constructor(private fb: FormBuilder) {}

  public signInForm = this.fb.group({
    correo: ['', [Validators.required, Validators.email]],
    contrasena: ['', [Validators.required, Validators.minLength(this.PASSWORD_LENGTH)]],
  })

  onSubmit() {
    if (!this.signInForm.valid) return

    const correo = this.signInForm.get('correo')?.value
    const contrasena = this.signInForm.get('contrasena')?.value

    if (!correo || !contrasena) return

    this.formSubmit.emit({
      email: correo,
      contrasena,
    })
  }
}
