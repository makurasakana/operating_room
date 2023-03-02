import { NgModule } from '@angular/core';
import { PatientComponent } from './patient/patient.component';
import { AddPatientComponent } from './patient/add-patient/add-patient.component';
import { MedicalWorkerComponent } from './medical-worker/medical-worker.component';
import { OperationComponent } from './operation/operation.component';
import { SharedModule } from '@shared/shared.module';
import { ResourceMtRoutingModule } from './resource-mt-routing.module';

@NgModule({
  imports: [SharedModule, ResourceMtRoutingModule],
  declarations: [PatientComponent, AddPatientComponent, MedicalWorkerComponent, OperationComponent],
})
export class ResourceMtModule {}
