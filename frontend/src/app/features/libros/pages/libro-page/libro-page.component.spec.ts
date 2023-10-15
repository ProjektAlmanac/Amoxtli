import { ComponentFixture, TestBed } from '@angular/core/testing'

import { LibroPageComponent } from './libro-page.component'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { RouterTestingModule } from '@angular/router/testing'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { LibroModule } from '../../libro.module'
import { SharedModule } from 'src/app/shared/shared.module'
import { MatSnackBarModule } from '@angular/material/snack-bar'

describe('IntercambioPageComponent', () => {
  let component: LibroPageComponent
  let fixture: ComponentFixture<LibroPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LibroPageComponent],
      imports: [
        LibroModule,
        NoopAnimationsModule,
        RouterTestingModule,
        HttpClientTestingModule,
        SharedModule,
        MatSnackBarModule,
      ],
    })
    fixture = TestBed.createComponent(LibroPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
