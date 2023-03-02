import { Component, OnInit, ElementRef } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ShowService } from './now.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-now',
  templateUrl: './now.component.html',
  styleUrls: ['./now.component.less'],
})
export class NowComponent implements OnInit, ElementRef {
  // 搜索表单
  formModel: FormGroup;
  // 分页
  currentPage = 1;
  perPage = 5;
  totalAmount = 0;
  // 展开收起
  expandForm = false;
  // 选中的信息
  loading = false;
  // 表格
  sInfo = '';

  // 表格
  allChecked = false;
  checkedNumber = 0;
  dataSet = [];
  indeterminate = false;
  canDel = true;
  selectIds = [];
  patient = '';
  operatingRoom = '';
  realName = '';
  anesthesiologist = '';
  operator = '';
  circulatingNurse = '';
  supporter = '';
  startTime = '';
  endTime = '';
  // 弹框
  isDeleteVisible = false; // 删除
  showALL: boolean;
  timer: NodeJS.Timer;

  state1 = '手术准备';
  state2 = '暂无手术';
  state3 = '暂无手术';
  state4 = '暂无手术';
  state5 = '暂无手术';
  state6 = '暂无手术';
  state7 = '暂无手术';
  state8 = '暂无手术';

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private msg: NzMessageService,
    private service: ShowService,
    private http: HttpClient,
    private el: ElementRef,
  ) {}
  nativeElement: any;

  ngOnInit() {
    this.searchInfo();
    this.timer = setInterval(() => {
      this.searchInfo();
    }, 1000);
  }
  submitForm(): void {
    this.http
      .get(
        `/operation/list?patient=${this.patient}&operatingRoom=${this.operatingRoom}&realName=${this.realName}
        &anesthesiologist=${this.anesthesiologist}&operator=${this.operator}&circulatingNurse=${this.circulatingNurse}
        &supporter=${this.supporter}&startTime=${this.startTime}&endTime=${this.endTime}
        &page=${this.currentPage}&pageSize=${this.perPage}`,
      )
      .subscribe((el: any) => {
        this.dataSet = el.data || [];
      });
  }

  // 查询||获取数据列表
  searchInfo() {
    const info = {
      page: this.currentPage,
      limit: this.perPage,
    };
    this.loading = true;
    this.service.getReportList(info).subscribe(
      v => {
        this.dataSet = v.data;
        this.totalAmount = v.count;
        this.loading = false;
      },
      err => {
        this.msg.error(err.error.message);
        this.loading = false;
      },
    );
  }

  clickTr(item, event) {
    event.stopPropagation();
    item.checked = !item.checked;
    this.refreshStatus();
  }

  refreshStatusE(event) {
    event.stopPropagation();
    this.refreshStatus();
  }

  refreshStatus(): void {
    const allChecked = this.dataSet.every(value => value.checked === true);
    const allUnChecked = this.dataSet.every(value => !value.checked);
    this.allChecked = allChecked;
    this.indeterminate = !allChecked && !allUnChecked;
    this.checkedNumber = this.dataSet.filter(value => value.checked).length;
    const arr = [];
    this.canDel = true;
    this.dataSet.forEach((val: any) => {
      if (val.checked) {
        arr.push(val.id);
        if (val.status !== 'APPLY') {
          this.canDel = false;
        }
      }
    });
    this.selectIds = arr;
  }
  checkAll(value: boolean): void {
    this.dataSet.forEach(data => (data.checked = value));
    this.refreshStatus();
  }

  // 重置
  resetInfo() {
    this.formModel.reset();
    this.currentPage = 1;
    this.searchInfo();
  }
  // 分页相关
  pageSizeChange(pageSize) {
    this.perPage = pageSize;
    this.currentPage = 1;
    this.searchInfo();
  }
  pageIndexChange(page) {
    this.currentPage = page;
    this.searchInfo();
  }
  showAllTemplate() {
    this.showALL = true;

    const fullscreenDiv = document.getElementById('showAll');

    let fullscreenFunc = fullscreenDiv.requestFullscreen;

    if (!fullscreenFunc) {
      [
        'mozRequestFullScreen',

        'msRequestFullscreen',

        'webkitRequestFullScreen',
        // tslint:disable-next-line:only-arrow-functions
      ].forEach(function(req) {
        fullscreenFunc = fullscreenFunc || fullscreenDiv[req];
      });
    }
    fullscreenFunc.call(fullscreenDiv);
  }

  goTo(location: string) {
    this.router.navigate(['/message/operation/now', location]);
  }
}
