import { ComponentFixture, TestBed } from '@angular/core/testing'

import { PerfilComponent } from './perfil.component'
import { RouterTestingModule } from '@angular/router/testing'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { MatSnackBarModule } from '@angular/material/snack-bar'
import { MatCardModule } from '@angular/material/card'
import { MatFormFieldModule } from '@angular/material/form-field'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { MatButtonModule } from '@angular/material/button'
import { MatInputModule } from '@angular/material/input'

describe('PerfilComponent', () => {
  let component: PerfilComponent
  let fixture: ComponentFixture<PerfilComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PerfilComponent],
      imports: [
        MatSnackBarModule,
        MatCardModule,
        MatFormFieldModule,
        FormsModule,
        NoopAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        MatButtonModule,
        HttpClientTestingModule,
        MatInputModule,
      ],
    })
    fixture = TestBed.createComponent(PerfilComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
