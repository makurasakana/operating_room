import { Injectable, Inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, BehaviorSubject } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class ShowService {
  constructor(
    private http: HttpClient,
    @Inject("BASE_CONFIG") private config
  ) {}

  // 获取手术列表
  getOperationList(data): Observable<any> {
    const url = `${this.config.url}/operation/list`;
    return this.http.get(url, { params: data }).pipe(map(res => res));
  }

}
