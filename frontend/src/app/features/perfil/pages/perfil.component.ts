import { Component, inject, OnInit } from '@angular/core'
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { successNotification } from '../../../shared/config/LibreryConfig'
import { DefaultService, PerfilUsuario } from 'src/generated/openapi'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import { Observable, zip } from 'rxjs'

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
      phoneNumber: ['', [Validators.required, Validators.minLength(10)]],
      interests: [''],
    })
  }
  //actualizar los datos en el momento
  ngOnInit(): void {
    this.showInputPhoto = false // Oculta el campo de foto después de una actualización exitosa
    this.showButtons = false
    this.resetTouchedFields()
    this.recuperaUsuario()
    //meter una variable que obtenga el ID para pasarselo
  }

  recuperaUsuario() {
    this.serviceApi.getUsuario(this.servicioUsuario.id.value).subscribe(usuario => {
      this.perfilUsuario = usuario
      this.imageSrc = usuario.fotoPerfil ?? ''

      // Llena los campos del formulario con la información del perfilUsuario
      this.form.patchValue({
        photo: this.imageSrc,
        describePhoto: usuario.descripcionFoto,
        name: usuario.nombre,
        lastname: usuario.apellidos,
        email: usuario.correo,
        phoneNumber: usuario.telefono,
        interests: usuario.intereses,
      })
    })
  }

  public success(msg: string) {
    successNotification(msg, this._snackBar)
  }
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  onFileSelected(event: Event) {
    const target = event.target as HTMLInputElement
    this.photoTouched = true
    this.selectedFile = target.files?.[0] ?? null

    // Obtener la extensión del archivo
    const nombreArchivo = this.selectedFile?.name
    const extension = nombreArchivo?.substring(nombreArchivo.lastIndexOf('.') + 1).toLowerCase()

    // Verificar si la extensión es JPEG o PNG
    if (extension === 'jpg' || extension === 'jpeg' || extension === 'png') {
      this.checkTouchedFielsd()
      if (this.selectedFile) {
        const reader = new FileReader()

        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        reader.onload = (e: any) => {
          this.imageSrc = e.target.result
        }
        reader.readAsDataURL(this.selectedFile)
      }
    } else {
      this.success('Por favor, seleccione una imagen de formato JPEG o PNG.')
      target.value = '' // Esto elimina el archivo seleccionado
    }
  }

  sendValues() {
    const id = this.perfilUsuario.id
    const nombre = this.form.get('name')?.value
    const apellidos = this.form.get('lastname')?.value
    const correo = this.form.get('email')?.value
    const telefono = this.form.get('phoneNumber')?.value
    let descripcionFoto = this.form.get('describePhoto')?.value
    let intereses = this.form.get('interest')?.value
    const fotoPerfil = this.form.get('photo')?.value
    const correoVerificado = this.perfilUsuario.correoVerificado
    if (
      descripcionFoto == null ||
      descripcionFoto == undefined ||
      intereses == null ||
      intereses == undefined
    ) {
      intereses = ''
      descripcionFoto = ''
    }
    const updateUser: PerfilUsuario = {
      id,
      nombre,
      apellidos,
      correo,
      telefono,
      descripcionFoto,
      intereses,
      fotoPerfil,
      correoVerificado,
    }
    this.actualizaUsuario(updateUser, this.selectedFile)
  }

  actualizaUsuario(updateUser: PerfilUsuario, foto: File | null) {
    const idUsuario = this.servicioUsuario.id.value

    let actualizacionDatos = this.serviceApi.actualizarUsuario(
      idUsuario,
      updateUser
    ) as Observable<unknown>

    if (foto !== null && this.photoTouched) {
      const actuailzacionFoto = this.serviceApi.actualizarFotoPerfil(idUsuario, foto)
      actualizacionDatos = zip(actualizacionDatos, actuailzacionFoto)
    }

    actualizacionDatos.subscribe({
      next: () => {
        this.showInputPhoto = false // Oculta el campo de foto después de una actualización exitosa
        this.showButtons = false
        this.resetTouchedFields()
        this.success('Se actualizaron correctamente los campos')
      },
      error: () => {
        this.success('No se actualizó correctamente en el servidor')
      },
    })
  }

  cancel() {
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
    } else {
      this.showButtons = false
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
