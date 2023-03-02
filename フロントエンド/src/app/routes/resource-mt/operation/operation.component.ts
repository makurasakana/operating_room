import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { OperationService } from './operation.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.less'],
})
export class OperationComponent implements OnInit {
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
  allAnesthesiologists = [];
  allPatients = [];
  allCirculatingNurses = [];
  allOperatingRooms = [];
  allOperators = [];
  allDepts = [];
  allNames = [];
  allTimes = [];
  patient = '';
  operatingRoom = '';
  name = '';
  anesthesiologist = '';
  operator = '';
  circulatingNurse = '';
  supporter = '';
  startTime = '';
  endTime = '';
  // 弹框
  isDeleteVisible = false; // 删除

  sortName = null;
  sortValue = null;
  searchDept = null;
  searchState = null;
  listOfDept = [
    { text: '儿科', value: '儿科' },
    { text: '神经外科', value: '神经外科' },
  ];
  listOfState = [
    { text: '手术准备', value: '手术准备' },
    { text: '患者进入手术室', value: '患者进入手术室' },
    { text: '麻醉开始', value: '麻醉开始' },
    { text: '手术开始', value: '手术开始' },
    { text: '等待复苏', value: '等待复苏' },
    { text: '手术结束', value: '手术结束' },
  ];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private msg: NzMessageService,
    private service: OperationService,
    private http: HttpClient,
  ) {}

  ngOnInit() {
    this.searchInfo();
    // this.getAllPatients();
    // this.getAllAnesthesiologists();
    // this.getAllOperators();
    // this.getAllRealNames();
    // this.getAllCirculatingNurses();
    // this.getAllOperatingRooms();
    // this.getAllOperators();
    // this.getAllStartTimes();
    // this.getAllEndTimes();
  }

  sortChange(sort: { key: string; value: string }): void {
    this.sortName = sort.key;
    this.sortValue = sort.value;
    this.searchInfo();
  }

  filter(searchDept: string, searchState: string): void {
    this.searchDept = searchDept;
    this.searchState = searchState;
    this.searchInfo();
  }

  change1(e) {
    this.patient = e;
  }
  change3(e) {
    this.name = e;
  }
  change4(e) {
    this.anesthesiologist = e;
  }
  change5(e) {
    this.operator = e;
  }
  change6(e) {
    this.circulatingNurse = e;
  }
  change7(e) {
    this.supporter = e;
  }
  change8(e) {
    this.startTime = e;
  }
  change9(e) {
    this.endTime = e;
  }
  submitForm(): void {
    this.http
      .get(
        `/operation/list?patient=${this.patient}&operatingRoom=${this.operatingRoom}&realName=
        ${this.name}&anesthesiologist=${this.anesthesiologist}
        &operator=${this.operator}&circulatingNurse=${this.circulatingNurse}
        &supporter=${this.supporter}&startTime=${this.startTime}&endTime=${this.endTime}
        &page=${this.currentPage}&pageSize=${this.perPage}`,
      )
      .subscribe((el: any) => {
        this.dataSet = el.data || [];
      });
  }

  // getAllPatients() {
  //   this.service.getAllPatient().subscribe((v: any) => {
  //     v.forEach(it => {
  //       this.allPatients.push({ code: it.id, name: it.name });
  //     });
  //   });
  // }
  // getAllAnesthesiologists() {
  //   this.service.getAllAnesthesiologist().subscribe((v: any) => {
  //     v.forEach(it => {
  //       this.allAnesthesiologists.push({ code: it.id, name: it.name });
  //     });
  //   });
  // }
  // getAllCirculatingNurses() {
  //   this.service.getAllCirculatingNurse().subscribe((v: any) => {
  //     v.forEach(it => {
  //       this.allCirculatingNurses.push({ code: it.id, name: it.name });
  //     });
  //   });
  // }
  // getAllOperators() {
  //   this.service.getAllOperator().subscribe((v: any) => {
  //     v.forEach(it => {
  //       this.allOperators.push({ code: it.id, name: it.name });
  //     });
  //   });
  // }
  // getAllRealNames() {
  //   this.service.getAllRealName().subscribe((v: any) => {
  //     v.forEach(it => {
  //       this.allNames.push({ code: it.id, name: it.name });
  //     });
  //   });
  // }

  // getAllStartTimes() {
  //   this.service.getAllStartTime().subscribe((v: any) => {
  //     v.forEach(it => {
  //       this.allStartTimes.push({ code: it.id, name: it.name });
  //     });
  //   });
  // }

  // getAllEndTimes() {
  //   this.service.getAllEndTime().subscribe((v: any) => {
  //     v.forEach(it => {
  //       this.allEndTimes.push({ code: it.id, name: it.name });
  //     });
  //   });
  // }
  // 查询||获取数据列表
  searchInfo() {
    const info = {
      page: this.currentPage,
      limit: this.perPage,
    };
    this.loading = true;
    this.service.getOperationList(info).subscribe(
      v => {
        const filterFunc = (item: { dept: string; status: string }) =>
          (this.searchDept ? item.dept.indexOf(this.searchDept) !== -1 : true) &&
          (this.searchState ? item.status.indexOf(this.searchState) !== -1 : true);
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

  // 新建
  add() {
    this.router.navigateByUrl('/operation/add');
  }

  // 修改
  edit() {
    this.router.navigate(['/operation/add'], {
      queryParams: {
        id: this.selectIds[0],
      },
    });
  }

  // 删除
  del() {
    this.isDeleteVisible = true;
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
