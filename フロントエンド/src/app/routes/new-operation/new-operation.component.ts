import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { _HttpClient } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd';
import { SFComponent, SFSchema, SFRadioWidgetSchema, SFSelectWidgetSchema } from '@delon/form';
import { of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { deepCopy } from '@delon/util';

@Component({
  selector: 'app-new-operation',
  templateUrl: './new-operation.component.html',
  styleUrls: ['./new-operation.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewOperationComponent implements OnInit {
  title = '新增手术信息';

  @Input()
  id: string;

  @ViewChild('sf', { static: false }) sf: SFComponent;
  A;

  formData = {};
  schema: SFSchema = {
    properties: {
      name: {
        type: 'string',
        title: '手术名',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      location: {
        type: 'string',
        title: '手术室',
        enum: [
          { label: '1', value: '1' },
          { label: '2', value: '2' },
          { label: '3', value: '3' },
          { label: '4', value: '4' },
          { label: '5', value: '5' },
          { label: '6', value: '6' },
          { label: '7', value: '7' },
          { label: '8', value: '8' },
        ],
        // tslint:disable-next-line:no-object-literal-type-assertion
        ui: {
          grid: {
            span: 16,
          },
          widget: 'select',
        } as SFSelectWidgetSchema,
      },
      pid: {
        type: 'string',
        format: 'pid',
        title: '住院号',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      diagnose: {
        type: 'string',
        title: '诊断',
        format: 'diagnose',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      operator: {
        type: 'string',
        format: 'operator',
        title: '施术者',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      circulatingNurse: {
        type: 'string',
        title: '巡回护士',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      anesthesiologist: {
        type: 'string',
        title: '麻醉医师',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
      supporter: {
        type: 'string',
        title: '协助者',
        ui: {
          grid: {
            span: 16,
          },
        },
      },
    },
    ui: {
      spanLabelFixed: 70,
      grid: {
        span: 24,
      },
    },
    required: ['name', 'location', 'pid', 'operator', 'circulatingNurse', 'anesthesiologist', 'supporter'],
  };
  layout = 'horizontal';

  constructor(
    private router: Router,
    private http: _HttpClient,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef,
    private activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {}

  submit() {
    let data = this.sf.value;
    this.http.post('/operation/add', data).subscribe((res: any) => {});
    this.msg.success('成功');
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
