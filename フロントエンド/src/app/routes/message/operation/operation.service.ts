import { Injectable, Inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, BehaviorSubject } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class OperationService {
  constructor(
    private http: HttpClient,
    @Inject("BASE_CONFIG") private config
  ) {}

  // 获取手术列表
  getOperationList(data): Observable<any> {
    const url = `/operation/list`;
    return this.http.get(url, { params: data }).pipe(map(res => res));
  }

  // 新增手术
  addOperation(data): Observable<any> {
    const url = `${this.config.url}/operation/add`;
    return this.http.post(url, data).pipe(map(res => res));
  }

  // 编辑手术
  editOperation(data): Observable<any> {
    const url = `${this.config.url}/operation/add`;
    return this.http.post(url, data).pipe(map(res => res));
  }

  // 删除手术
  delOperation(data): Observable<any> {
    const url = `${this.config.url}/operation/delete?id=` + data.id;
    return this.http.delete(url).pipe(map(res => res));
  }

  // // 获取患者列表
  // getAllPatient(): Observable<any> {
  //   const url = `${this.config.url}/status`;
  //   return this.http.get(url).pipe(map(res => res));
  // }

  // // 获取手术室列表
  // getAllOperatingRoom(): Observable<any> {
  //   const url = `${this.config.url}/operation`;
  //   return this.http.get(url).pipe(map(res => res));
  // }

  // // 获取实施手术名列表
  // getAllRealName(): Observable<any> {
  //   const url = `${this.config.url}/operation`;
  //   return this.http.get(url).pipe(map(res => res));
  // }

  // // 获取麻醉医师列表
  // getAllAnesthesiologist(): Observable<any> {
  //   const url = `${this.config.url}/operation`;
  //   return this.http.get(url).pipe(map(res => res));
  // }

  // // 获取施术者列表
  // getAllOperator(): Observable<any> {
  //   const url = `${this.config.url}/operation`;
  //   return this.http.get(url).pipe(map(res => res));
  // }

  // // 获取巡回护士列表
  // getAllCirculatingNurse(): Observable<any> {
  //   const url = `${this.config.url}/operation`;
  //   return this.http.get(url).pipe(map(res => res));
  // }

  // // 获取起始时间列表
  // getAllStartTime(): Observable<any> {
  //   const url = `${this.config.url}/operation`;
  //   return this.http.get(url).pipe(map(res => res));
  // }

  // // 获取结束时间列表
  // getAllEndTime(): Observable<any> {
  //   const url = `${this.config.url}/operation`;
  //   return this.http.get(url).pipe(map(res => res));
  // }
}
