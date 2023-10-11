import { ComponentFixture, TestBed } from '@angular/core/testing'

import { InvolucradosComponent } from './involucrados.component'

describe('InvolucradosComponent', () => {
  let component: InvolucradosComponent
  let fixture: ComponentFixture<InvolucradosComponent>

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InvolucradosComponent],
    })
    fixture = TestBed.createComponent(InvolucradosComponent)
    component = fixture.componentInstance
    fixture.detectChanges()
  })

  it('should create', () => {
    expect(component).toBeTruthy()
  })
})
