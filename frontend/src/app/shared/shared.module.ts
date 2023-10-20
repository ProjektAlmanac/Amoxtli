import { MatListModule } from '@angular/material/list'
import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'
import { RouterModule } from '@angular/router'

import { NavBarComponent } from './components/nav-bar/nav-bar.component'
import { ProgressSpinnerComponent } from './components/progress-spinner/progress-spinner.component'

import { MatToolbarModule } from '@angular/material/toolbar'
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatMenuModule } from '@angular/material/menu'
import { MatIconModule } from '@angular/material/icon'
import { MatButtonModule } from '@angular/material/button'
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner'
import { AuthenticatedComponent } from './components/authenticated/authenticated.component'

@NgModule({
  declarations: [NavBarComponent, ProgressSpinnerComponent, AuthenticatedComponent],
  imports: [
    CommonModule,
    MatButtonModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatMenuModule,
    RouterModule,
    MatProgressSpinnerModule,
  ],
  exports: [NavBarComponent, ProgressSpinnerComponent],
})
export class SharedModule {}
