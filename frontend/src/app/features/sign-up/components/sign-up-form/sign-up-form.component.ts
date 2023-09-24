import { Component, EventEmitter, Input, Output } from '@angular/core'
import { FormControl, FormGroup, Validators } from '@angular/forms'
import { Usuario } from 'src/generated/openapi'

@Component({
  selector: 'app-sign-up-form',
  templateUrl: './sign-up-form.component.html',
  styleUrls: ['./sign-up-form.component.sass'],
})
export class SignInFormComponent {
  @Output()
  formSubmit = new EventEmitter<Usuario>()

  @Input()
  mensajeError = ''

  readonly PASSWORD_LENGTH = 8

  showPassword = false

  signInForm = new FormGroup({
    name: new FormControl('', Validators.required),
    surname: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(this.PASSWORD_LENGTH),
    ]),
    confirmPassword: new FormControl('', [Validators.required, confirmPasswordValidator]),
  })

  public onSubmit() {
    if (!this.signInForm.valid) return

    const nombre = this.signInForm.value.name
    const apellidos = this.signInForm.value.surname
    const correo = this.signInForm.value.email
    const password = this.signInForm.value.password

    if (!nombre || !apellidos || !correo || !password) return

    this.formSubmit.emit({
      apellildos: apellidos,
      correo,
      nombre,
      password,
    })
  }
}

function confirmPasswordValidator(control: FormControl): { [s: string]: boolean } | null {
  const password = control.root.get('password')
  const confirmPassword = control.root.get('confirmPassword')

  if (!password?.value || !confirmPassword?.value) return null

  if (password.value === confirmPassword.value) return null
  return {
    confirmPassword: true,
  }
}
