import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { PatientService } from './patient.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.less'],
})
export class PatientComponent implements OnInit {
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
  searchState = null;
  listOfGender = [
    { text: '男', value: '男' },
    { text: '女', value: '女' },
  ];
  listOfDept = [
    { text: '儿科', value: '儿科' },
    { text: '神经外科', value: '神经外科' },
  ];
  listOfState = [
    { text: '住院', value: '住院' },
    { text: '手术中', value: '手术中' },
    { text: '已出院', value: '已出院' },
  ];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private msg: NzMessageService,
    private service: PatientService,
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

  filter(searchGender: string, searchDept: string, searchState: string): void {
    this.searchGender = searchGender;
    this.searchDept = searchDept;
    this.searchState = searchState;
    this.searchInfo();
  }

  change1(e) {
    this.dept = e;
  }

  submitForm(): void {
    this.http
      .get(`/patient/list?dept=${this.dept}&page=${this.currentPage}&pageSize=${this.perPage}`)
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
    const info = {
      page: this.currentPage,
      limit: this.perPage,
    };
    this.loading = true;
    this.service.getPatientList(info).subscribe(
      v => {
        const filterFunc = (item: { sex: string; dept: string; state: string }) =>
          (this.searchGender ? item.sex.indexOf(this.searchGender) !== -1 : true) &&
          (this.searchDept ? item.dept.indexOf(this.searchDept) !== -1 : true) &&
          (this.searchState ? item.state.indexOf(this.searchState) !== -1 : true);
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

  detail() {
    this.router.navigateByUrl('/message/patient/detail');
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
