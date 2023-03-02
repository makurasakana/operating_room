import { Component, OnInit } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AllService } from './all.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-all',
  templateUrl: './all.component.html',
  styleUrls: ['./all.component.less'],
})
export class AllComponent implements OnInit {
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
  allUsers = [];
  allOperations = [];
  allTypes = [];
  user = '';
  operation = '';
  type = '';
  isDeleteVisible = false;
  _searchOid = null;
  sortName = null;
  sortValue = null;
  searchName = null;
  searchType = null;
  listOfName = [
    { text: '张三', value: '张三' },
    { text: '赵二', value: '赵二' },
  ];
  listOfType = [
    { text: '流程', value: '流程' },
    { text: '描述', value: '描述' },
    { text: '突发', value: '突发' },
  ];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private msg: NzMessageService,
    private service: AllService,
    private http: HttpClient,
  ) {}

  ngOnInit() {
    this.searchInfo();
    // this.getAllUsers();
    // this.getAllOperations();
  }

  sortChange(sort: { key: string; value: string }): void {
    this.sortName = sort.key;
    this.sortValue = sort.value;
    this.searchInfo();
  }
  filter(searchName: string, searchType: string): void {
    this.searchName = searchName;
    this.searchType = searchType;
    this.searchInfo();
  }

  change(e) {
    this.operation = e;
  }
  submitForm(): void {
    this.http
      .get(
        `/message/list?user=${this.user}&operation=${this.operation}&type=${this.type}&page=${this.currentPage}&pageSize=${this.perPage}`,
      )
      .subscribe((el: any) => {
        this.dataSet = el.data || [];
      });
  }

  //  // 获取所有用户信息
  //  getAllUsers() {
  //   this.service.getAllUser().subscribe((v: any) => {
  //     v.forEach(it => {
  //       this.allUsers.push({ code: it.id, name: it.name });
  //     });
  //   });
  // }
  // // 获取所有手术信息
  // getAllOperations() {
  //   this.service.getAllOperation().subscribe((v: any) => {
  //     v.forEach(it => {
  //       this.allOperations.push({ code: it.id, name: it.name });
  //     });
  //   });
  // }
  // 查询||获取数据列表
  searchInfo() {
    // this.dataSet = [{ id: 1, bh: 1, xq: 1 }];
    const info = {
      page: this.currentPage,
      limit: this.perPage,
    };
    this.loading = true;
    this.service.getAllList(info).subscribe(
      v => {
        const filterFunc = (item: { name: string; type: string }) =>
          this.searchName
            ? item.name.indexOf(this.searchName) !== -1
            : true && this.searchType
            ? item.type.indexOf(this.searchType) !== -1
            : true;
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

  detail(item: any) {
    //   this.router.navigateByUrl(`/app/event/detail?id=${item.id}`);
    this.router.navigate(['/event/detail'], {
      queryParams: {
        id: item.id,
      },
    });
  }
}
