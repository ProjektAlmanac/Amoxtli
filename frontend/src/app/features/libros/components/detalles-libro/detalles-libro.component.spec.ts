import { ComponentFixture, TestBed } from '@angular/core/testing'

import { DetallesLibroComponent } from './detalles-libro.component'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { librosModule } from '../../libros.module'

describe('DetallesLibroComponent', () => {
  let component: DetallesLibroComponent
  let fixture: ComponentFixture<DetallesLibroComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetallesLibroComponent],
      imports: [NoopAnimationsModule, RouterTestingModule, HttpClientTestingModule, librosModule],
    })
    fixture = TestBed.createComponent(DetallesLibroComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
