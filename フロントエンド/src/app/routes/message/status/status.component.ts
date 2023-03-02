import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { StatusService } from './status.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.less'],
})
export class StatusComponent implements OnInit {
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
  allOperations = [];
  allPatients = [];
  patient = '';
  operation = '';
  searchName = null;
  // 弹框
  isDeleteVisible = false; // 删除
  _searchOid = null;
  _searchPid = null;
  sortName = null;
  sortValue = null;
  listOfName = [
    { text: '张三', value: '张三' },
    { text: '赵二', value: '赵二' },
  ];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private msg: NzMessageService,
    private service: StatusService,
    private http: HttpClient,
  ) {}

  ngOnInit() {
    this.searchInfo();
  }

  sortChange(sort: { key: string; value: string }): void {
    this.sortName = sort.key;
    this.sortValue = sort.value;
    this.searchInfo();
  }
  filter(searchName: string): void {
    this.searchName = searchName;
    this.searchInfo();
  }
  change1(e) {
    this.patient = e;
  }
  change2(e) {
    this.operation = e;
  }
  submitForm(): void {
    this.http
      .get(
        `/status/list?patient=${this.patient}&operation=${this.operation}&page=${this.currentPage}&pageSize=${this.perPage}`,
      )
      .subscribe((el: any) => {
        this.dataSet = el.data || [];
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
    this.service.getStatusList(info).subscribe(
      v => {
        const filterFunc = (item: { name: string }) =>
          this.searchName ? item.name.indexOf(this.searchName) !== -1 : true;
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
  addInfo() {
    this.router.navigateByUrl('/status/add');
  }

  // 修改
  editInfo() {
    this.router.navigate(['/status/add'], {
      queryParams: {
        id: this.selectIds[0],
      },
    });
  }

  // 删除
  delInfo() {
    this.isDeleteVisible = true;
  }

  // 删除确定
  deleteInfo() {
    const info = {
      id: this.selectIds.join(','),
    };
    this.service.delStatus(info).subscribe(
      v => {
        this.msg.success(v.message);
        // 判断是否需要调整页面
        if ((this.allChecked || this.dataSet.length <= 1) && this.currentPage > 1) {
          this.currentPage = this.currentPage - 1;
        }
        // 刷新数据
        this.allChecked = false;
        this.indeterminate = false;
        this.isDeleteVisible = false;
        this.selectIds = [];
        this.checkedNumber = 0;
        this.searchInfo();
      },
      err => {
        this.msg.error(err.error.message);
        this.isDeleteVisible = false;
      },
    );
  }

  // 删除取消
  handleCancel($event) {
    this.isDeleteVisible = false;
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
