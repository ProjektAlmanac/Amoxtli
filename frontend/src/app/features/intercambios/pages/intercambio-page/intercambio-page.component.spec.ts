import { ComponentFixture, TestBed } from '@angular/core/testing'

import { IntercambioPageComponent } from './intercambio-page.component'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { RouterTestingModule } from '@angular/router/testing'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { IntercambiosModule } from '../../intercambios.module'
import { SharedModule } from 'src/app/shared/shared.module'
import { MatSnackBarModule } from '@angular/material/snack-bar'

describe('IntercambioPageComponent', () => {
  let component: IntercambioPageComponent
  let fixture: ComponentFixture<IntercambioPageComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IntercambioPageComponent],
      imports: [
        IntercambiosModule,
        NoopAnimationsModule,
        RouterTestingModule,
        HttpClientTestingModule,
        SharedModule,
        MatSnackBarModule,
      ],
    })
    fixture = TestBed.createComponent(IntercambioPageComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
