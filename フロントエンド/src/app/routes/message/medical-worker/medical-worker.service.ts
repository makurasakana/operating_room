import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class MedicalWorkerService {
  constructor(private http: HttpClient, @Inject('BASE_CONFIG') private config) {}

  // 获取医护人员列表
  getMedicalWorkersList(data): Observable<any> {
    const url = `${this.config.url}/medicalWorker/list`;
    return this.http.get(url, { params: data }).pipe(map(res => res));
  }

  // 新增医护人员
  addMedicalWorker(data): Observable<any> {
    const url = `${this.config.url}/medicalWorker/add`;
    return this.http.post(url, data).pipe(map(res => res));
  }

  // 编辑医护人员
  editMedicalWorker(data): Observable<any> {
    const url = `${this.config.url}/medicalWorker/add`;
    return this.http.post(url, data).pipe(map(res => res));
  }

  // 删除医护人员
  delMedicalWorker(data): Observable<any> {
    const url = `${this.config.url}/medicalWorker/delete?id=` + data.id;
    return this.http.delete(url).pipe(map(res => res));
  }

  // 获取部门列表
  getAllDept(): Observable<any> {
    const url = `${this.config.url}/dept/getDept`;
    return this.http.get(url).pipe(map(res => res));
  }
}
