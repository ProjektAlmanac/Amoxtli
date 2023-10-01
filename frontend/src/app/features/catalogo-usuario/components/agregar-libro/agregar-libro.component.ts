import { Component, NgZone, ViewChild } from '@angular/core'
import {
  FormBuilder,
  Validators,
  AbstractControl,
  ValidatorFn,
  ValidationErrors,
} from '@angular/forms'
import { CdkTextareaAutosize } from '@angular/cdk/text-field'
import { DefaultService, DetallesLibro, LibroRegistrado } from 'src/generated/openapi'
import { ServicioUsuario } from 'src/app/core/services/servicio-usuario.service'
import { take } from 'rxjs'

@Component({
  selector: 'app-agregar-libro',
  templateUrl: './agregar-libro.component.html',
  styleUrls: ['./agregar-libro.component.sass'],
})
export class AgregarLibroComponent {
  public libro!: DetallesLibro
  public mostrarCardLibro = false
  public mostrarSpinner = false
  public mostrarNotificacionExito = false
  public mostrarNotificacionWarn = false
  public mostrarNotificacionError = false

  constructor(
    private fb: FormBuilder,
    private apiService: DefaultService,
    private _ngZone: NgZone,
    private servicioUsuario: ServicioUsuario
  ) {}

  isbnInputForm = this.fb.group({
    isbn: ['', [Validators.required, isbnLengthValidator()]],
  })

  descripcionForm = this.fb.group({
    descripcion: ['', [Validators.required]],
  })

  onSubmit() {
    this.mostrarNotificacionExito = false
    this.mostrarNotificacionWarn = false
    this.mostrarNotificacionError = false
    this.mostrarCardLibro = false
    this.mostrarSpinner = true

    if (!this.isbnInputForm.valid) {
      this.mostrarSpinner = false
      this.isbnInputForm.markAllAsTouched()
      return
    }
    this.descripcionForm.reset()

    const isbn = this.isbnInputForm.get('isbn')?.value

    if (isbn !== null && isbn !== undefined) this.buscarLibro(isbn)
  }

  buscarLibro(isbn: string) {
    this.apiService.getDetallesLibro(isbn).subscribe({
      next: libro => {
        this.libro = libro
        this.mostrarCardLibro = true
        this.mostrarSpinner = false
      },
      error: () => {
        this.mostrarNotificacionWarn = true
        this.mostrarSpinner = false
      },
    })
  }

  agregarLibro() {
    this.mostrarSpinner = true
    if (!this.descripcionForm.valid) {
      this.descripcionForm.markAllAsTouched()
      this.mostrarSpinner = false
      return
    }

    const id = 0
    const isbn = this.isbnInputForm.get('isbn')?.value
    const descripcion = this.descripcionForm.get('descripcion')?.value

    if (isbn !== null && isbn !== undefined && descripcion !== null && descripcion !== undefined) {
      const libro: LibroRegistrado = {
        id,
        isbn,
        descripcion,
      }
      this.agregarLibroCalotogoUsuario(libro)
    }
  }

  agregarLibroCalotogoUsuario(libro: LibroRegistrado) {
    const idUsuario = this.servicioUsuario.id.value
    this.apiService.addLibro(idUsuario, libro).subscribe({
      next: () => {
        this.mostrarCardLibro = false
        this.mostrarSpinner = false
        this.mostrarNotificacionExito = true
        this.isbnInputForm.reset()
      },
      error: () => {
        this.mostrarNotificacionError = true
        this.mostrarSpinner = false
      },
    })
  }

  cancelar() {
    this.mostrarCardLibro = false
    this.isbnInputForm.reset()
  }

  @ViewChild('autosize') autosize!: CdkTextareaAutosize

  triggerResize() {
    this._ngZone.onStable.pipe(take(1)).subscribe(() => this.autosize.resizeToFitContent(true))
  }
}

function isbnLengthValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const inputValue: string = control.value
    if (inputValue.length === 10 || inputValue.length === 13) {
      return null
    } else {
      return { isbnLength: true }
    }
  }
}
