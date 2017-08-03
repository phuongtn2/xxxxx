import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';

import { UserService } from '../user.service';
import { User } from '../../core/models/user';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
  providers: [UserService]
})
export class UsersComponent implements OnInit {
  title: string;
  users: User[];
  limit = 10;

  constructor(
    private titleService: Title,
    private userService: UserService
  ) {
    this.title = 'Danh sách nhân sự';
  }

  ngOnInit() {
    this.setTitle();
    this.getUsers();
  }

  private getUsers() {
    this.userService.getAll()
      .subscribe(
        (users) => {
          this.users = users;
        }
      );
  }

  private setTitle() {
    this.titleService.setTitle(this.title);
  }
}
