import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-global-variables-overview',
  templateUrl: './global-variables-overview.component.html',
  styleUrls: ['./global-variables-overview.component.css']
})
export class GlobalVariablesOverviewComponent implements OnInit {
  displayedColumns: string[] = ['type', 'subtype', 'name', 'description', 'deletable'];
  dataSource: GlobalVariableDTO[] = [];

  constructor() {

    for (let i = 0; i < 30; i++) {
      this.dataSource.push({id: 'id-' + i, type: 'type-' + i, subtype: 'subtype-' + i, name: 'name-' + i, description: 'description-' + i, deletable: true})
    }

  }

  ngOnInit(): void {
  }
}

interface DefaultDTOObject {
  id?: string;
  version?: Date;
}

interface GlobalVariableDTO extends DefaultDTOObject {
  type: string;
  subtype: string;
  name: string;
  description?: string;
  deletable?: boolean;
}
