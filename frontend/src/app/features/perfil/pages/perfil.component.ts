import { Component, inject, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { successNotification } from '../../../shared/config/LibreryConfig'
import { DefaultService, PerfilUsuario } from 'src/generated/openapi'

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.sass'],
})
export class PerfilComponent implements OnInit {
  public perfilUsuario!: PerfilUsuario
  public photoImg = ''
  private _snackBar = inject(MatSnackBar)

  form: FormGroup

  //Variable to control the appearance of the buttons
  showButtons = false

  //Variable to control the photo
  showInputPhoto = false

  //State variables to know if a field has been manipulated
  photoTouched = false
  describePhotoTouched = false
  nameTouched = false
  lastnameTouched = false
  emailTouched = false
  phoneNumberTouched = false
  interestsTouched = false

  constructor(
    private fb: FormBuilder,
    private serviceApi: DefaultService
  ) {
    this.form = this.fb.group({
      photo: [''],
      describePhoto: [''],
      name: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
      interests: [''],
    })
  }
  //actualizar los datos en el momento
  ngOnInit(): void {
    this.recuperaUsuario()
    //meter una variable que obtenga el ID para pasarselo
    // eslint-disable-next-line no-console
  }

  recuperaUsuario() {
    this.serviceApi.getUsuario(1).subscribe(usuario => {
      this.perfilUsuario = usuario
      this.photoImg = usuario.fotoPerfil ?? ''
      // eslint-disable-next-line no-console
      console.log(typeof usuario.fotoPerfil)

      // Llena los campos del formulario con la información del perfilUsuario
      this.form.patchValue({
        describePhoto: usuario.descripcinFoto,
        name: usuario.nombre,
        lastname: usuario.apellidos,
        email: usuario.correo,
        phoneNumber: usuario.telfono,
        interests: usuario.intereses,
        photo: usuario.fotoPerfil,
      })
    })
  }

  public success(msg: string) {
    successNotification(msg, this._snackBar)
  }

  sendValues() {
    //eslint-disable-next-line no-console
    console.log('Datos actualizados')
    this.success('Datos actualizados')
    const actualizacionExitosa = true
    if (actualizacionExitosa) {
      this.showInputPhoto = false // Oculta el campo de foto después de una actualización exitosa
      this.showButtons = false
    }
    this.form.reset()
  }

  cancel() {
    // eslint-disable-next-line no-console
    console.log('Se canceló')
    this.form.reset()
    this.recuperaUsuario()
    this.showButtons = false
    this.showInputPhoto = false
    this.success('No se actualizaron los datos')
    //falta poner un metodo GET para mostrar la informacion que tiene
  }
  mostrarCampoDeFoto() {
    this.showInputPhoto = true //show the input photo
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
        this.nameTouched = true
        break
      case 4:
        this.lastnameTouched = true
        break
      case 5:
        this.emailTouched = true
        break
      case 6:
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
      this.nameTouched ||
      this.lastnameTouched ||
      this.emailTouched ||
      this.phoneNumberTouched ||
      this.interestsTouched
    ) {
      this.showButtons = true
    }
  }
  resetTouchedFields() {
    this.photoTouched = false
    this.describePhotoTouched = false
    this.nameTouched = false
    this.lastnameTouched = false
    this.emailTouched = false
    this.phoneNumberTouched = false
    this.interestsTouched = false
  }
}
