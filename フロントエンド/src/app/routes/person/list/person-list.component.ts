import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { _HttpClient } from '@delon/theme';
import { NzModalService } from 'ng-zorro-antd';
import { PersonDetailComponent } from '../detail/person-detail.component';

@Component({
  selector: 'snails-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PersonListComponent implements OnInit {
  data: any[] = [];
  orgunitList: any[] = [];

  pageNumber = 1;
  pageSize = 15;
  totalPages = 0;
  totalNumber = 0;
  dataOnLoading = false;
  new = '123456';

  sortValue = {};
  _searchUsername: '';
  searchOrgunit = null;
  searchGender = null;

  listOfGender = [
    { text: '男', value: '男' },
    { text: '女', value: '女' },
  ];

  listOfOrgunit = [
    { text: '巡回护士', value: '巡回护士' },
    { text: '麻醉医师', value: '麻醉医师' },
    { text: '系统管理员', value: '系统管理员' },
    { text: '后台工作人员', value: '后台工作人员' },
  ];

  constructor(
    private router: Router,
    private http: _HttpClient,
    private module: NzModalService,
    private cdr: ChangeDetectorRef,
  ) {
    this.initOrgunitList();
  }

  ngOnInit(): void {
    this.search();
  }

  filter(searchGender: string, searchOrgunit: string): void {
    this.searchGender = searchGender;
    this.searchOrgunit = searchOrgunit;
    this.search();
  }

  search() {
    this.dataOnLoading = true;

    let param = {
      pageNumber: this.pageNumber - 1,
      pageSize: this.pageSize,
    };

    let body = {
      username: this._searchUsername || '',
      ...this.sortValue,
    };

    this.http.post('/person/account/find', body, param).subscribe(
      (res: any) => {
        this.dataOnLoading = false;

        if (res.status === 0) {
          this.data = res.data.content as any[];
          const filterFunc = (item: { gender: string; orgunit: string }) =>
            (this.searchGender ? item.gender.indexOf(this.searchGender) !== -1 : true) &&
            (this.searchOrgunit ? item.orgunit.indexOf(this.searchOrgunit) !== -1 : true);
          this.data = this.data.filter(item => filterFunc(item));
          this.totalPages = res.data.totalPages;
          this.totalNumber = res.data.totalElements;

          this.cdr.detectChanges();
        }
      },
      error => {
        this.dataOnLoading = false;
      },
    );
  }

  keydown($event: KeyboardEvent) {
    if ($event.key == 'Enter') {
      this.search();
    }
  }

  delete(obj: any) {
    this.http.get(`/person/account/delete/${obj.id}`).subscribe((res: any) => {
      if (res.status === 0) {
        this.search();
      }
    });
  }

  edit(obj: any) {
    this.module.create({
      nzTitle: '编辑用户',
      nzComponentParams: { id: obj.id },
      nzContent: PersonDetailComponent,
      nzMaskClosable: true,
      nzWidth: 900,
      nzOnOk: () => {
        this.search();
      },
      nzBodyStyle: {
        padding: '10px',
      },
      nzFooter: null,
    });
  }

  nzPageIndexChange($event: number) {
    this.pageNumber = $event;
    this.search();
  }

  create() {
    this.module.create({
      nzTitle: '新增用户',
      nzComponentParams: {},
      nzContent: PersonDetailComponent,
      nzMaskClosable: true,
      nzWidth: 900,
      nzOnOk: () => {
        this.search();
      },
      nzBodyStyle: {
        padding: '10px',
      },
      nzFooter: null,
    });
  }

  reset() {
    this._searchUsername = '';

    this.search();
  }

  sortChange($event: { key: string; value: string }) {
    this.sortValue[$event.key] = $event.value;
    this.search();
  }

  getOrgunitName(id: string) {
    let title = this.orgunitList.find(item => item.id === id);
    if (title) {
      return title.title;
    }
    return '';
  }

  getRealPassword(old: string) {
    return this.new;
  }

  private initOrgunitList() {
    this.http.get('/orgunit/list').subscribe(
      (res: any) => {
        if (res.status === 0) {
          let d = res.data as any[];
          this.orgunitList = d;
        }
      },
      error => {},
    );
  }
}
