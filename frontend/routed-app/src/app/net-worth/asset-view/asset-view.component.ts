import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';


@Component({
  selector: 'app-asset-view',
  templateUrl: './asset-view.component.html',
  styleUrls: ['./asset-view.component.css']
})
export class AssetViewComponent implements OnInit {
  // declare data-models for this component
  // if we intend to inject values, the data-model MUST be an @Input
  @Input() price:number = 0
  @Input() code:string = ''
  @Input() index?:number // ? means it might not exist
  count:number = 0
  // declare a custom event for this component
  @Output() sortie:EventEmitter<number> = new EventEmitter()
  @Output() sellEvent:EventEmitter<number> = new EventEmitter()
  @Output() codeChangeEvent:EventEmitter<object> = new EventEmitter()
  constructor() { }

  ngOnInit(): void {
  }
  // here are the methods of this component
  buyStock(){
    // increment the count
    this.count += 1
    // emit our custom event to the parent component
    this.sortie.emit(this.price) // we can send complex data as an object
  }
  sellStock(){
    // increment the count
    if (this.count>0) {
     this.count -= 1
     this.sortie.emit(-this.price)
    //  this.sellEvent.emit(this.price)
    }
  }
  resetStock(){
    // reset THIS stock
    let delta = -this.price * this.count
    this.sortie.emit(delta)
    this.count = 0
  }
  codeChangeHandler(){
    this.codeChangeEvent.emit({code:this.code, index:this.index})
  }
}
