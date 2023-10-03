import { Component, inject, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { successNotification } from '../../../shared/config/LibreryConfig'
import { DefaultService, PerfilUsuario } from 'src/generated/openapi'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.sass'],
})
export class PerfilComponent implements OnInit {
  public perfilUsuario!: PerfilUsuario
  private _snackBar = inject(MatSnackBar)
  selectedFile: File | null = null
  imageSrc: string | ArrayBuffer | null = null
  form: FormGroup
  originalUserData!: PerfilUsuario

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
    private serviceApi: DefaultService,
    private servicioUsuario: ServicioUsuario
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
      // Hacer una copia de los datos originales
      this.originalUserData = { ...usuario }
      this.imageSrc = usuario.fotoPerfil ?? ''
      // eslint-disable-next-line no-console
      console.log(typeof usuario.fotoPerfil)

      // Llena los campos del formulario con la información del perfilUsuario
      this.form.patchValue({
        photo: this.imageSrc,
        describePhoto: usuario.descripcinFoto,
        name: usuario.nombre,
        lastname: usuario.apellidos,
        email: usuario.correo,
        phoneNumber: usuario.telfono,
        interests: usuario.intereses,
      })
    })
  }

  public success(msg: string) {
    successNotification(msg, this._snackBar)
  }
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  onFileSelected(event: any) {
    // eslint-disable-next-line no-console
    console.log('captura archivo')
    // eslint-disable-next-line no-console
    console.log(event.target.files)
    this.selectedFile = event.target.files[0]
    // eslint-disable-next-line no-console
    console.log(this.selectedFile)
    //const file: File = event.target.files[0]
    // Obtener la extensión del archivo
    const nombreArchivo = this.selectedFile?.name
    const extension = nombreArchivo?.substring(nombreArchivo.lastIndexOf('.') + 1).toLowerCase()

    // Verificar si la extensión es JPEG o PNG
    if (extension === 'jpg' || extension === 'jpeg' || extension === 'png') {
      // eslint-disable-next-line no-console
      console.log('¡El archivo es de formato JPEG o PNG y ha sido seleccionado correctamente!')
      if (this.selectedFile) {
        const reader = new FileReader()

        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        reader.onload = (e: any) => {
          this.imageSrc = e.target.result
        }

        reader.readAsDataURL(this.selectedFile)
        // eslint-disable-next-line no-console
        console.log(this.selectedFile)
        // eslint-disable-next-line no-console
        console.log(reader)
      }
    } else {
      // eslint-disable-next-line no-console
      this.success('Por favor, seleccione una imagen de formato JPEG o PNG.')
      // eslint-disable-next-line no-console
      console.log('Por favor, seleccione una imagen de formato JPEG o PNG.')
      // También puedes limpiar el campo de entrada para que el usuario seleccione nuevamente
      event.target.value = '' // Esto elimina el archivo seleccionado
    }
  }

  /*sendValues() {
    //eslint-disable-next-line no-console
    console.log('Datos actualizados')
    this.success('Datos actualizados')
    if (!this.form.valid) {
      this.showInputPhoto = false // Oculta el campo de foto después de una actualización exitosa
      this.showButtons = false
      return
    }
    this.form.reset()
  }*/
  sendValues() {
    const id = this.perfilUsuario.id
    const nombre = this.form.get('name')?.value
    const apellidos = this.form.get('lastname')?.value
    const correo = this.form.get('email')?.value
    const telfono = this.form.get('phoneNumber')?.value
    let descripcinFoto = this.form.get('describePhoto')?.value
    let intereses = this.form.get('interest')?.value
    const fotoPerfil = this.form.get('photo')?.value
    const correoVerificado = this.perfilUsuario.correoVerificado
    if (
      descripcinFoto == null ||
      descripcinFoto == undefined ||
      intereses == null ||
      intereses == undefined
    ) {
      intereses = ''
      descripcinFoto = ''
    }
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const updateUser: PerfilUsuario = {
      id,
      nombre,
      apellidos,
      correo,
      telfono,
      descripcinFoto,
      intereses,
      fotoPerfil,
      correoVerificado,
    }
    this.actualizaUsuario(updateUser, fotoPerfil)
    // eslint-disable-next-line no-console
    console.log(typeof id)
    // eslint-disable-next-line no-console
    console.log(typeof nombre)
    // eslint-disable-next-line no-console
    console.log(typeof apellidos)
    // eslint-disable-next-line no-console
    console.log(typeof correo)
    // eslint-disable-next-line no-console
    console.log(typeof telfono)
    // eslint-disable-next-line no-console
    console.log(typeof descripcinFoto)
    // eslint-disable-next-line no-console
    console.log(typeof intereses)
    // eslint-disable-next-line no-console
    console.log(typeof fotoPerfil)
    // eslint-disable-next-line no-console
    console.log(typeof correoVerificado)
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  actualizaUsuario(updateUser: PerfilUsuario, foto: string) {
    const idUsuario = this.servicioUsuario.id.value
    this.serviceApi.actualizarUsuario(idUsuario, updateUser).subscribe({
      next: () => {
        this.showInputPhoto = false // Oculta el campo de foto después de una actualización exitosa
        this.showButtons = false
        this.resetTouchedFields()
      },
      error: () => {
        this.success('No se actualizó correctamente en el servidor')
      },
    })
    const id = idUsuario.toString()
    this.serviceApi.actualizarFotoPerfil(id).subscribe({
      next: () => {
        this.success('Se actualizó correctamente la foto')
      },
      error: () => {
        this.success('No se actualizó correctamente en el servidor')
      },
    })
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
