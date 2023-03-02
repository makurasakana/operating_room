import { Injectable, Inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, BehaviorSubject } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class StatusService {
  constructor(
    private http: HttpClient,
    @Inject("BASE_CONFIG") private config
  ) {}

  // 获取状态列表
  getStatusList(data): Observable<any> {
    const url = `${this.config.url}/status/list`;
    return this.http.get(url, { params: data }).pipe(map(res => res));
  }


  // 新增状态
  addStatus(data): Observable<any> {
    const url = `${this.config.url}/status/add`;
    return this.http.post(url, data).pipe(map(res => res));
  }

  // 编辑状态
  editStatus(data): Observable<any> {
    const url = `${this.config.url}/status/add`;
    return this.http.post(url, data).pipe(map(res => res));
  }

  // 删除状态
  delStatus(data): Observable<any> {
    const url = `${this.config.url}/status/delete?id=` + data.id;
    return this.http.delete(url).pipe(map(res => res));
  }

}
