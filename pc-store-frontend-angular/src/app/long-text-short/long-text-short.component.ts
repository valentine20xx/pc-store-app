import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-long-text-short',
  templateUrl: './long-text-short.component.html',
  styleUrls: ['./long-text-short.component.scss']
})
export class LongTextShortComponent implements OnInit {
  @Input()
  text?: string;

  @Input()
  factor = 20

  @Input()
  showAllButton = false;

  @Input()
  url?: string;

  full = false;

  constructor() {
  }

  ngOnInit(): void {
  }

  showAll(): void {
    this.full = true;
  }
}
