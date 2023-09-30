import { Component, EventEmitter, Output } from '@angular/core'
import { FormControl, FormGroup, Validators } from '@angular/forms'

@Component({
  selector: 'app-verify-email-form',
  templateUrl: './verify-email-form.component.html',
  styleUrls: ['./verify-email-form.component.sass'],
})
export class VerifyEmailFormComponent {
  @Output()
  formSubmit = new EventEmitter<{ code: string }>()

  verifyEmailForm = new FormGroup({
    code: new FormControl('', [Validators.required]),
  })

  onSubmit() {
    const code = this.verifyEmailForm.value.code
    if (!code) return
    this.formSubmit.emit({ code })
  }
}
