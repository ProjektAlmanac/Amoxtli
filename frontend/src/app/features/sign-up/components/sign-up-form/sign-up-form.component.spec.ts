import { ComponentFixture, TestBed } from '@angular/core/testing'

import { SignUpFormComponent } from './sign-up-form.component'
import { SignUpModule } from '../../sign-up.module'
import { NoopAnimationsModule } from '@angular/platform-browser/animations'

describe('SignUpFormComponent', () => {
  let component: SignUpFormComponent
  let fixture: ComponentFixture<SignUpFormComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SignUpFormComponent],
      imports: [SignUpModule, NoopAnimationsModule],
    })
    fixture = TestBed.createComponent(SignUpFormComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
