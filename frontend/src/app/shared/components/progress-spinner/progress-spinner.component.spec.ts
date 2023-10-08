import { ComponentFixture, TestBed } from '@angular/core/testing'

import { ProgressSpinnerComponent } from './progress-spinner.component'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'

describe('ProgressSpinnerComponent', () => {
  let component: ProgressSpinnerComponent
  let fixture: ComponentFixture<ProgressSpinnerComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgressSpinnerComponent],
      imports: [MatProgressSpinnerModule],
    })
    fixture = TestBed.createComponent(ProgressSpinnerComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
