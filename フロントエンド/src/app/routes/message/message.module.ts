import { NgModule } from '@angular/core';
// import { RealTimeComponent } from "./real-time/real-time.component";
import { AllComponent } from './all/all.component';
import { PatientComponent } from './patient/patient.component';
import { DetailComponent } from './patient/detail/detail.component';
import { MedicalWorkerComponent } from './medical-worker/medical-worker.component';
import { OperationComponent } from './operation/operation.component';
import { NowComponent } from './now/now.component';
import { OperationNowComponent } from './operation-now/operation-now.component';
import { StatusComponent } from '../message/status/status.component';
import { SharedModule } from '@shared/shared.module';
import { MessageRoutingModule } from './message-routing.module';

@NgModule({
  imports: [SharedModule, MessageRoutingModule],
  declarations: [
    NowComponent,
    OperationNowComponent,
    AllComponent,
    StatusComponent,
    PatientComponent,
    DetailComponent,
    MedicalWorkerComponent,
    OperationComponent,
  ],
})
export class MessageModule {}
