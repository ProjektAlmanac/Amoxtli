import { MatListModule } from '@angular/material/list'
import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common'

import { MatToolbarModule } from '@angular/material/toolbar'
import { MatSidenavModule } from '@angular/material/sidenav'
import { MatMenuModule } from '@angular/material/menu'
import { NavBarComponent } from './components/nav-bar/nav-bar.component'
import { MatIconModule } from '@angular/material/icon'
import { RouterModule } from '@angular/router'
import { MatButtonModule } from '@angular/material/button'

@NgModule({
  declarations: [NavBarComponent],
  imports: [
    CommonModule,
    MatButtonModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatMenuModule,
    RouterModule,
  ],
  exports: [NavBarComponent],
})
export class SharedModule {}
