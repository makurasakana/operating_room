import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NowComponent } from './now/now.component';
import { OperationNowComponent } from './operation-now/operation-now.component';
import { AllComponent } from './all/all.component';
import { PatientComponent } from './patient/patient.component';
import { DetailComponent } from './patient/detail/detail.component';
import { MedicalWorkerComponent } from './medical-worker/medical-worker.component';
import { OperationComponent } from './operation/operation.component';
import { StatusComponent } from '../message/status/status.component';
const routes: Routes = [
  { path: 'now', component: NowComponent },
  { path: 'operation/now/:location', component: OperationNowComponent },
  { path: 'operation/now/1', component: OperationNowComponent },
  { path: 'all', component: AllComponent },
  { path: 'status', component: StatusComponent },
  { path: 'medical-worker', component: MedicalWorkerComponent },
  { path: 'patient', component: PatientComponent },
  { path: 'patient/detail', component: DetailComponent },
  { path: 'operation', component: OperationComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MessageRoutingModule {}
