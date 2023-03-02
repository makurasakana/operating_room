import { NgModule } from '@angular/core';

import { SharedModule } from '@shared';
import { RouteRoutingModule } from './routes-routing.module';
// dashboard pages
import { DashboardV1Component } from './dashboard/v1/v1.component';
// passport pages
import { UserLoginComponent } from './passport/login/login.component';
// single pages
import { UserLockComponent } from './passport/lock/lock.component';
import { CallbackComponent } from './callback/callback.component';
import { DashboardV2Component } from './dashboard/v2/v2.component';

import { ShowComponent } from './message/show/show.component';
import { NewOperationComponent } from './new-operation/new-operation.component';

const COMPONENTS = [
  DashboardV1Component,
  DashboardV2Component,
  NewOperationComponent,
  // passport pages
  UserLoginComponent,
  // single pages
  UserLockComponent,
  CallbackComponent,
  ShowComponent,
];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [SharedModule, RouteRoutingModule],
  declarations: [...COMPONENTS, ...COMPONENTS_NOROUNT],
  entryComponents: COMPONENTS_NOROUNT,
})
export class RoutesModule {}
