import { Injectable, Inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, BehaviorSubject } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class PatientService {
  constructor(
    private http: HttpClient,
    @Inject("BASE_CONFIG") private config
  ) {}

  // 获取患者列表
  getPatientList(data): Observable<any> {
    const url = `${this.config.url}/patient/list`;
    return this.http.get(url, { params: data }).pipe(map(res => res));
  }

  // 新增患者
  addPatient(data): Observable<any> {
    const url = `${this.config.url}/patient/add`;
    return this.http.post(url, data).pipe(map(res => res));
  }

  // 编辑患者
  editPatient(data): Observable<any> {
    const url = `${this.config.url}/patient/add`;
    return this.http.post(url, data).pipe(map(res => res));
  }

  // 删除患者
  delPatient(data): Observable<any> {
    const url = `${this.config.url}/patient/delete?id=` + data.id;
    return this.http.delete(url).pipe(map(res => res));
  }

  // 获取部门列表
  getAllDept(): Observable<any> {
    const url = `${this.config.url}/dept/getDept`;
    return this.http.get(url).pipe(map(res => res));
  }

  // 获取患者详情
  getPatientDetail(data): Observable<any> {
    const url = `${this.config.url}/patient/detail/` + data.id;
    return this.http.get(url).pipe(map(res => res));
  }
}
