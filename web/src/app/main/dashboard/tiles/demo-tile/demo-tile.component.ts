import { Component, OnInit } from '@angular/core';
import { ContentTile } from '../../custom-tile-page';

@Component({
  selector: 'app-demo-tile',
  templateUrl: './demo-tile.component.html',
  styleUrls: ['./demo-tile.component.scss']
})
@ContentTile({ key: 'DEMO' })
export class DemoTileComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
