import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { _HttpClient } from '@delon/theme';
import { NzMessageService, NzModalRef } from 'ng-zorro-antd';
import { SFComponent, SFRadioWidgetSchema } from '@delon/form';
import { of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { deepCopy } from '@delon/util';

@Component({
  selector: 'snails-person-detail',
  templateUrl: './person-detail.component.html',
  styleUrls: ['./person-detail.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PersonDetailComponent implements OnInit {
  @Input()
  id: string;

  @ViewChild('sf', { static: false }) sf: SFComponent;

  formData = {};
  schema = {
    properties: {
      username: {
        type: 'string',
        title: '用户名',
      },
      password: {
        type: 'string',
        title: '密码',
      },
      gender: {
        type: 'string',
        title: '性别',
        // tslint:disable-next-line:no-object-literal-type-assertion
        ui: {
          widget: 'radio',
          asyncData: () =>
            of([
              { label: '男', value: '男' },
              { label: '女', value: '女' },
            ]).pipe(delay(100)),
          grid: {
            span: 12,
          },
        } as SFRadioWidgetSchema,
        default: '',
      },
    },
    ui: {
      spanLabelFixed: 70,
      grid: {
        span: 24,
      },
    },
    required: ['username', 'password', 'gender'],
  };
  layout = 'horizontal';

  constructor(
    private router: Router,
    private http: _HttpClient,
    private modalRef: NzModalRef,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    if (this.id) {
      this.http.get(`/person/account/detail/${this.id}`).subscribe((res: any) => {
        if (res.status === 0) {
          this.formData = deepCopy(res.data);
          this.sf.rootProperty.resetValue(this.formData, false);
          this.cdr.detectChanges();
        } else {
          this.msg.error('记录或已被删除！');
        }
      });
    }
  }

  submit() {
    let data = this.sf.value;
    if (data['id']) {
      this.http.post('/person/account/edit', data).subscribe((res: any) => {
        this.modalRef.triggerOk();
      });
    } else {
      this.http.post('/person/account/create', data).subscribe((res: any) => {
        this.modalRef.triggerOk();
      });
    }
  }

  /**
   * 表单数据变更
   * @param data 变更的值
   */
  onFormChange(data) {
    return;
  }

  onFormError(event) {}
}
