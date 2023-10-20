import { ComponentFixture, TestBed } from '@angular/core/testing'

import { HomePageComponent } from './home-page.component'
import { CatalogoUsuarioModule } from '../../catalogo-usuario.module'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { RouterTestingModule } from '@angular/router/testing'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'

describe('HomePageComponent', () => {
  let component: HomePageComponent
  let fixture: ComponentFixture<HomePageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomePageComponent],
      imports: [
        CatalogoUsuarioModule,
        NoopAnimationsModule,
        RouterTestingModule,
        HttpClientTestingModule,
      ],
    })
    fixture = TestBed.createComponent(HomePageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
