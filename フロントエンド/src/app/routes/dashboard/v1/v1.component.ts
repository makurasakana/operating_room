import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { _HttpClient } from '@delon/theme';
import { zip } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { RouterHelper } from '@core/helper/RouterHelper';

@Component({
  selector: 'app-dashboard-v1',
  templateUrl: './v1.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DashboardV1Component implements OnInit {
  currentOperationNum = 3;
  userNum = 3;
  patientNum = 3;
  medicalWorkerNum = 9;
  operationData: any[];

  constructor(private http: _HttpClient, private routerHelper: RouterHelper, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.mockData();
    zip(
      // this.http.post(`/operation/currentOperationNum`),
      this.http.get('/person/num'),
      // this.http.get('/patient/num'),
      // this.http.get('/medical-worker/num'),
    )
      .pipe(
        // 接收其他拦截器后产生的异常消息
        catchError(([currentOperationNum, userNum, patientNum, medicalWorkerNum]) => {
          return [currentOperationNum, userNum, patientNum, medicalWorkerNum];
        }),
      )
      .subscribe(([currentOperationNum, userNum, patientNum, medicalWorkerNum]) => {
        this.currentOperationNum = currentOperationNum.data;
        this.userNum = userNum.data;
        this.patientNum = patientNum.data;
        this.medicalWorkerNum = medicalWorkerNum.data;

        this.mockData();

        this.cdr.detectChanges();
      });
  }

  mockData() {
    const _operationData = [];
    for (let i = 5; i < 17; i += 1) {
      _operationData.push({
        x: `4月${i + 1}日`,
        y: Math.floor(Math.random() * 10) + 20,
      });
    }
    this.operationData = _operationData;
  }
}
