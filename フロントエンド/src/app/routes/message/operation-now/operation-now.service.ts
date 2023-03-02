import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ShowService {
  constructor(private http: HttpClient, @Inject('BASE_CONFIG') private config) {}

  // 获取上报信息列表
  getReportList(data): Observable<any> {
    const url = `${this.config.url}/report/now`;
    return this.http.get(url, { params: data }).pipe(map(res => res));
  }

  // 获取状态列表
  getStatusList(data): Observable<any> {
    const url = `${this.config.url}/status/now`;
    return this.http.get(url, { params: data }).pipe(map(res => res));
  }
}
