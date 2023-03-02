import { Component, ChangeDetectionStrategy } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { _HttpClient } from '@delon/theme';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DetailComponent {
  // 返回
  toFront() {
    history.go(-1);
  }

  constructor(
    private http: _HttpClient,
    private msg: NzMessageService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) {}
}
