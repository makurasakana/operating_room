import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { _HttpClient } from '@delon/theme';
import { NzMessageService, NzModalRef } from 'ng-zorro-antd';
import { SFComponent, SFSchema, SFRadioWidgetSchema, SFSelectWidgetSchema } from '@delon/form';
import { of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { deepCopy } from '@delon/util';

@Component({
  selector: 'app-add-patient',
  templateUrl: './add-patient.component.html',
  styleUrls: ['./add-patient.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AddPatientComponent implements OnInit {
  title = '新增患者信息';

  @Input()
  id: string;

  @ViewChild('sf', { static: false }) sf: SFComponent;
  A;

  formData = {};
  schema: SFSchema = {
    properties: {
      idCard: {
        type: 'string',
        title: '身份证号',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      sex: {
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
      name: {
        type: 'string',
        title: '姓名',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      age: {
        type: 'string',
        title: '年龄',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      state: {
        type: 'string',
        title: '状态',
        enum: [
          { label: '住院', value: '住院' },
          { label: '手术中', value: '手术中' },
          { label: '已出院', value: '一出院' },
        ],
        // tslint:disable-next-line:no-object-literal-type-assertion
        ui: {
          grid: {
            span: 16,
          },
          widget: 'select',
        } as SFSelectWidgetSchema,
      },
      dept: {
        type: 'string',
        title: '科室',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      bed: {
        type: 'string',
        title: '床位号',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      allergy: {
        type: 'string',
        title: '药物过敏史',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      history: {
        type: 'string',
        title: '详细病史',
        ui: {
          widget: 'textarea',
          autosize: { minRows: 3, maxRows: 10 },
        },
      },
    },
    ui: {
      spanLabelFixed: 70,
      grid: {
        span: 24,
      },
    },
    required: ['name', 'idCard', 'sex', 'age', 'stste'],
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
      this.http.get(`/patient/detail/${this.id}`).subscribe((res: any) => {
        this.formData = deepCopy(res.data);
        this.sf.rootProperty.resetValue(this.formData, false);
        this.cdr.detectChanges();
      });
    }
  }

  submit() {
    let data = this.sf.value;
    this.http.post('/patient/add', data).subscribe((res: any) => {
      this.modalRef.triggerOk();
    });
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
