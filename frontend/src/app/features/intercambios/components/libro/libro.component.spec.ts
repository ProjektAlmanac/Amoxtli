import { ComponentFixture, TestBed } from '@angular/core/testing'

import { LibroComponent } from './libro.component'
import { IntercambiosModule } from '../../intercambios.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { RouterTestingModule } from '@angular/router/testing'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { SharedModule } from 'src/app/shared/shared.module'
import { MatSnackBarModule } from '@angular/material/snack-bar'

describe('LibroComponent', () => {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  let component: LibroComponent
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  let fixture: ComponentFixture<LibroComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LibroComponent],
      imports: [
        IntercambiosModule,
        NoopAnimationsModule,
        RouterTestingModule,
        HttpClientTestingModule,
        SharedModule,
        MatSnackBarModule,
      ],
    })
    fixture = TestBed.createComponent(LibroComponent)
  })

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  it('should create', () => {})
})
