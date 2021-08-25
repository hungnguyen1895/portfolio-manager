import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cash-flow',
  templateUrl: './cash-flow.component.html',
  styleUrls: ['./cash-flow.component.css']
})
export class CashFlowComponent implements OnInit {
  now = new Date()
  constructor() { }

  ngOnInit(): void {
    console.log('here I am!')

  }

}
