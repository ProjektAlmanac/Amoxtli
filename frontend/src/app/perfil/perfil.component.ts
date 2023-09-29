import { Component, inject } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { successNotification } from '../shared/config/LibreryConfig'

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.sass'],
})
export class PerfilComponent {
  private _snackBar = inject(MatSnackBar)

  form: FormGroup

  //Variable to control the appearance of the buttons
  showButtons = false

  //State variables to know if a field has been manipulated
  photoTouched = false
  describePhotoTouched = false
  nameUserTouched = false
  nameTouched = false
  lastnameTouched = false
  emailTouched = false
  phoneNumberTouched = false
  interestsTouched = false

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      photo: [''],
      describePhoto: [''],
      nameUser: ['', Validators.required],
      name: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
      interests: [''],
    })
  }

  public success(msg: string) {
    successNotification(msg, this._snackBar)
  }

  sendValues() {
    // eslint-disable-next-line no-console
    console.log('Datos actualizados')
    this.success('Datos actualizados')
    this.form.reset()
  }
  cancel() {
    // eslint-disable-next-line no-console
    console.log('Se canceló')
    this.form.reset()
    this.showButtons = false
    this.success('No se actualizaron los datos')
    //falta poner un metodo GET para mostrar la informacion que tiene
  }

  //Function to handle the ngModelChange event of the fields
  onChange(option: number) {
    switch (option) {
      case 1:
        this.photoTouched = true
        break
      case 2:
        this.describePhotoTouched = true
        break
      case 3:
        this.nameUserTouched = true
        break
      case 4:
        this.nameTouched = true
        break
      case 5:
        this.lastnameTouched = true
        break
      case 6:
        this.emailTouched = true
        break
      case 7:
        this.phoneNumberTouched = true
        break
      default:
        this.interestsTouched = true
        break
    }
  }
  checkTouchedFielsd() {
    if (
      this.photoTouched ||
      this.describePhotoTouched ||
      this.nameUserTouched ||
      this.nameTouched ||
      this.lastnameTouched ||
      this.emailTouched ||
      this.phoneNumberTouched ||
      this.interestsTouched
    ) {
      this.showButtons = true
    }
  }
}
