import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UserDetail, UserServiceService } from 'src/app/generated/rest';
import { ContentTile } from '../../custom-tile-page';

@Component({
  selector: 'app-user-info-tile',
  templateUrl: './user-info-tile.component.html',
  styleUrls: ['./user-info-tile.component.scss']
})
@ContentTile({ key: 'USER_INFO' })
export class UserInfoTileComponent implements OnInit {

  subscription: Subscription;
  userDetail: UserDetail;

  constructor(private userService: UserServiceService) { }

  ngOnInit(): void {
    this.subscription = this.userService.getUserDetail().subscribe(data => {
      this.userDetail = data;
    });
  }

}
