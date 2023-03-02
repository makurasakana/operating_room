import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { MedicalWorkerService } from './medical-worker.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-medical-worker',
  templateUrl: './medical-worker.component.html',
  styleUrls: ['./medical-worker.component.less'],
})
export class MedicalWorkerComponent implements OnInit {
  // 搜索表单
  formModel: FormGroup;
  // 分页
  currentPage = 1;
  perPage = 10;
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
  allDepts = [];
  dept = '';
  // 弹框
  isDeleteVisible = false; // 删除
  _searchUsername = null;
  sortName = null;
  sortValue = null;
  searchGender = null;
  searchDept = null;
  searchPosition = null;
  listOfGender = [
    { text: '男', value: '男' },
    { text: '女', value: '女' },
  ];
  listOfDept = [
    { text: '儿科', value: '儿科' },
    { text: '神经外科', value: '神经外科' },
    { text: '麻醉科', value: '麻醉科' },
  ];
  listOfPosition = [
    { text: '巡回护士', value: '巡回护士' },
    { text: '麻醉医师', value: '麻醉医师' },
    { text: '主任医师', value: '主任医师' },
    { text: '护士长', value: '护士长' },
  ];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private msg: NzMessageService,
    private service: MedicalWorkerService,
    private http: HttpClient,
  ) {}

  ngOnInit() {
    this.searchInfo();
    this.getAllDepts();
  }

  sortChange(sort: { key: string; value: string }): void {
    this.sortName = sort.key;
    this.sortValue = sort.value;
    this.searchInfo();
  }

  filter(searchGender: string, searchPosition: string, searchDept: string): void {
    this.searchGender = searchGender;
    this.searchPosition = searchPosition;
    this.searchDept = searchDept;
    this.searchInfo();
  }

  change1(e) {
    this.dept = e;
  }

  submitForm(): void {
    this.http
      .get(`/medicalWorker/findByDept?dept=${this.dept}&page=${this.currentPage}&limit=${this.perPage}`)
      .subscribe((el: any) => {
        this.dataSet = el.data || [];
      });
  }

  getAllDepts() {
    this.service.getAllDept().subscribe((v: any) => {
      v.forEach(it => {
        this.allDepts.push({ code: it.id, name: it.name });
      });
    });
  }

  // 查询||获取数据列表
  searchInfo() {
    // this.dataSet = [{ id: 1, bh: 1, xq: 1 }];
    const info = {
      page: this.currentPage,
      limit: this.perPage,
    };
    this.loading = true;
    this.service.getMedicalWorkersList(info).subscribe(
      v => {
        const filterFunc = (item: { sex: string; position: string; dept: string }) =>
          (this.searchGender ? item.sex.indexOf(this.searchGender) !== -1 : true) &&
          (this.searchPosition ? item.position.indexOf(this.searchPosition) !== -1 : true) &&
          (this.searchDept ? item.dept.indexOf(this.searchDept) !== -1 : true);
        const data = v.data.filter(item => filterFunc(item));
        if (this.sortName && this.sortValue) {
          this.dataSet = data.sort((a, b) =>
            this.sortValue === 'ascend'
              ? a[this.sortName] > b[this.sortName]
                ? 1
                : -1
              : b[this.sortName] > a[this.sortName]
              ? 1
              : -1,
          );
        } else {
          this.dataSet = data;
        }
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
}
