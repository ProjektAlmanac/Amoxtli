import { ComponentFixture, TestBed } from '@angular/core/testing'

import { AgregarLibroComponent } from './agregar-libro.component'

describe('AgregarLibroComponent', () => {
  let component: AgregarLibroComponent
  let fixture: ComponentFixture<AgregarLibroComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AgregarLibroComponent],
    })
    fixture = TestBed.createComponent(AgregarLibroComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
