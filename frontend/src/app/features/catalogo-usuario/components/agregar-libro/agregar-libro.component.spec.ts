import { ComponentFixture, TestBed } from '@angular/core/testing'

import { AgregarLibroComponent } from './agregar-libro.component'
import { RouterTestingModule } from '@angular/router/testing'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { UserModule } from '../../catalogo-usuario.module'
import { HttpClientTestingModule } from '@angular/common/http/testing'

describe('AgregarLibroComponent', () => {
  let component: AgregarLibroComponent
  let fixture: ComponentFixture<AgregarLibroComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AgregarLibroComponent],
      imports: [UserModule, NoopAnimationsModule, RouterTestingModule, HttpClientTestingModule],
    })
    fixture = TestBed.createComponent(AgregarLibroComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
