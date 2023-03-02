import { Component, OnInit, ElementRef } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ShowService } from './operation-now.service';
import { HttpClient } from '@angular/common/http';
// tslint:disable-next-line:no-duplicate-imports
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-operation-now',
  templateUrl: './operation-now.component.html',
  styleUrls: ['./operation-now.component.less'],
})
export class OperationNowComponent implements OnInit, ElementRef {
  // 搜索表单
  formModel: FormGroup;
  // 分页
  currentPage = 1;
  perPage = 5;
  totalAmount = 0;
  location = this.route.snapshot.paramMap.get('location');
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
  dataSet1 = [];
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
  showALL: boolean;
  timer: NodeJS.Timer;

  data5 = [];

  colorMap = {
    收缩压: '#2FC25B',
    舒张压: '#c28274',
    心率: '#FFD700',
    呼吸频率: '#DDA0DD',
    体温: '#DDA0DD',
  };

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private msg: NzMessageService,
    private service: ShowService,
    private http: HttpClient,
    private el: ElementRef,
    private route: ActivatedRoute,
  ) {}
  nativeElement: any;

  ngOnInit() {
    this.searchInfo();
    this.initData();
    this.timer = setInterval(() => {
      this.searchInfo();
    }, 1000);
    this.timer = setInterval(() => {
      this.initData();
    }, 10000);
  }

  // 查询||获取数据列表
  searchInfo() {
    const info = {
      page: this.currentPage,
      limit: this.perPage,
      location: this.location,
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
    this.service.getStatusList(info).subscribe(
      v => {
        this.dataSet1 = v.data;
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
      // tslint:disable-next-line:only-arrow-functions
      ['mozRequestFullScreen', 'msRequestFullscreen', 'webkitRequestFullScreen'].forEach(function(req) {
        fullscreenFunc = fullscreenFunc || fullscreenDiv[req];
      });
    }
    fullscreenFunc.call(fullscreenDiv);
  }

  // 返回
  toFront() {
    history.go(-1);
  }

  initData() {
    const info = [];
    const data = [];
    // this.service.getStatusList(info).subscribe(
    //   v => {
    //     this.dataSet2 = v.data;
    //     this.totalAmount = v.count;
    //     this.loading = false;
    //   },
    //   err => {
    //     this.msg.error(err.error.message);
    //     this.loading = false;
    //   },
    // );
    for (let i = 5; i < 10; i++) {
      data.push({
        x: '12:1' + i,
        y: 122,
        z: '收缩压',
      });
      data.push({
        x: '12:1' + i,
        y: 89,
        z: '舒张压',
      });
      data.push({
        x: '12:1' + i,
        y: 80,
        z: '心率',
      });
      data.push({
        x: '12:1' + i,
        y: 40,
        z: '呼吸频率',
      });
      data.push({
        x: '12:1' + i,
        y: 36.5,
        z: '体温',
      });
      data.push({
        x: '12:1' + i,
        y: 98,
        z: '氧饱和',
      });
    }
    this.data5 = data;
  }
}
