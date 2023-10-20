import { ComponentFixture, TestBed } from '@angular/core/testing'

import { AuthenticatedComponent } from './authenticated.component'
import { SharedModule } from '../../shared.module'

describe('AuthenticatedComponent', () => {
  let component: AuthenticatedComponent
  let fixture: ComponentFixture<AuthenticatedComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AuthenticatedComponent],
      imports: [SharedModule],
    })
    fixture = TestBed.createComponent(AuthenticatedComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
