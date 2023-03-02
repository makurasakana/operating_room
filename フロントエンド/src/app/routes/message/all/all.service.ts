import { Injectable, Inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, BehaviorSubject } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class AllService {
  constructor(
    private http: HttpClient,
    @Inject("BASE_CONFIG") private config
  ) {}

  // 获取消息列表
  getAllList(data): Observable<any> {
    const url = `${this.config.url}/report/list`;
    return this.http.get(url, { params: data }).pipe(map(res => res));
  }

  // // 获取手术列表
  // getAllOperation(): Observable<any> {
  //   const url = `${this.config.url}/report`;
  //   return this.http.get(url).pipe(map(res => res));
  // }
  // // 获取用户列表
  // getAllUser(): Observable<any> {
  //   const url = `${this.config.url}/report`;
  //   return this.http.get(url).pipe(map(res => res));
  // }

}
