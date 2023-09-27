import { Component } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.sass'],
})
export class PerfilComponent {
  form: FormGroup

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      photo: [''],
      describePhoto: [''],
      nameUser: ['', Validators.required],
      name: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.minLength(10)]],
      interests: [''],
    })
  }

  sendValues() {
    // eslint-disable-next-line no-console
    console.log('Datos actualizados')
    this.form.reset()
  }
  cancel() {
    // eslint-disable-next-line no-console
    console.log('Se canceló')
    this.form.reset()
  }
}
