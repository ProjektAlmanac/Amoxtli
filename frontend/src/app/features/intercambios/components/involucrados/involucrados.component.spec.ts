import { ComponentFixture, TestBed } from '@angular/core/testing'

import { InvolucradosComponent } from './involucrados.component'
import { IntercambiosModule } from '../../intercambios.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'
import { RouterTestingModule } from '@angular/router/testing'
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { SharedModule } from 'src/app/shared/shared.module'
import { MatSnackBarModule } from '@angular/material/snack-bar'

describe('InvolucradosComponent', () => {
  let component: InvolucradosComponent
  let fixture: ComponentFixture<InvolucradosComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InvolucradosComponent],
      imports: [
        IntercambiosModule,
        NoopAnimationsModule,
        RouterTestingModule,
        HttpClientTestingModule,
        SharedModule,
        MatSnackBarModule,
      ],
    })
    fixture = TestBed.createComponent(InvolucradosComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
