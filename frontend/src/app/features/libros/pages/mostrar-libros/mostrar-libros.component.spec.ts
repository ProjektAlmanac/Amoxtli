import { ComponentFixture, TestBed } from '@angular/core/testing'
import { MostrarLibrosComponent } from './mostrar-libros.component'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'

describe('MostrarLibrosComponent', () => {
  let component: MostrarLibrosComponent
  let fixture: ComponentFixture<MostrarLibrosComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MostrarLibrosComponent],
      imports: [NoopAnimationsModule, RouterTestingModule, HttpClientTestingModule],
    })
    fixture = TestBed.createComponent(MostrarLibrosComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
