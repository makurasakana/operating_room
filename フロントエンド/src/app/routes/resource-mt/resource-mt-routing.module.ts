import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PatientComponent } from './patient/patient.component';
import { AddPatientComponent } from './patient/add-patient/add-patient.component';
import { MedicalWorkerComponent } from './medical-worker/medical-worker.component';
import { OperationComponent } from './operation/operation.component';
const routes: Routes = [
  { path: 'medical-worker', component: MedicalWorkerComponent },
  { path: 'patient', component: PatientComponent },
  { path: 'patient/add', component: AddPatientComponent },
  { path: 'operation', component: OperationComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ResourceMtRoutingModule {}
