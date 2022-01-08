import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-global-variables-overview',
  templateUrl: './global-variables-overview.component.html',
  styleUrls: ['./global-variables-overview.component.css']
})
export class GlobalVariablesOverviewComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['type', 'subtype', 'name', 'description', 'deletable'];
  dataSource = new MatTableDataSource<GlobalVariableDTO>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor() {
    for (let i = 0; i < 23; i++) {
      this.dataSource.data.push({id: 'id-' + i, type: 'type-' + i, subtype: 'subtype-' + i, name: 'name-' + i, description: 'description-' + i, deletable: true})
    }

  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
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
