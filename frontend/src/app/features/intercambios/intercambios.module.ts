import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { NgIf } from '@angular/common'
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { RouterModule } from '@angular/router'

import { SharedModule } from 'src/app/shared/shared.module'

import { TextFieldModule } from '@angular/cdk/text-field'

import { MatButtonModule } from '@angular/material/button'
import { MatCardModule } from '@angular/material/card'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatIconModule } from '@angular/material/icon'
import { MatInputModule } from '@angular/material/input'
import { MatListModule } from '@angular/material/list'
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatToolbarModule } from '@angular/material/toolbar'
import { NavListComponent } from './components/nav-list/nav-list.component'
import { TarjetaComponent } from './components/tarjeta/tarjeta.component'

import { IntercambioPageComponent } from './pages/intercambio-page/intercambio-page.component'

@NgModule({
  declarations: [IntercambioPageComponent, NavListComponent, TarjetaComponent],
  imports: [
    CommonModule,
    FormsModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule,
    NgIf,
    ReactiveFormsModule,
    RouterModule,
    SharedModule,
    TextFieldModule,
  ],
  exports: [IntercambioPageComponent],
})
export class IntercambiosModule {}
