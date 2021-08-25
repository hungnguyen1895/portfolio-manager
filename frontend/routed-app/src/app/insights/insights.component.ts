import { Component, OnInit } from '@angular/core';
import { TypicodeService } from 'src/services/typicode.service';

@Component({
  selector: 'app-insights',
  templateUrl: './insights.component.html',
  styleUrls: ['./insights.component.css']
})
export class InsightsComponent implements OnInit {
  // we need data models for this component
  // reportData:any = {} // this is where our returned data from the API will go
  reportData = {name:'', id:1}
  // category:string = 'user'
  // id:number = 1
  paramObj = {category:'user', id:1}
  // we need access to the service
  constructor(private typicodeService:TypicodeService) { }

  ngOnInit(): void {
  }
  makeServiceCall(){
    // we call the service method by subscribing to it
    // remember the api call will be async so subscribing responds when it returns
    // this.typicodeService.getApiData({category:this.category, id:this.id})
    this.typicodeService.getApiData(this.paramObj)
      .subscribe( (data:any)=>{
        this.reportData = data
      } )
  }

}
