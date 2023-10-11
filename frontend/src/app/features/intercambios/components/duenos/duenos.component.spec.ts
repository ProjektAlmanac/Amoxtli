import { ComponentFixture, TestBed } from '@angular/core/testing'

import { DuenosComponent } from './duenos.component'
import { IntercambiosModule } from '../../intercambios.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { RouterTestingModule } from '@angular/router/testing'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { SharedModule } from 'src/app/shared/shared.module'
import { MatSnackBarModule } from '@angular/material/snack-bar'

describe('DuenosComponent', () => {
  let component: DuenosComponent
  let fixture: ComponentFixture<DuenosComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DuenosComponent],
      imports: [
        IntercambiosModule,
        NoopAnimationsModule,
        RouterTestingModule,
        HttpClientTestingModule,
        SharedModule,
        MatSnackBarModule,
      ],
    })
    fixture = TestBed.createComponent(DuenosComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
