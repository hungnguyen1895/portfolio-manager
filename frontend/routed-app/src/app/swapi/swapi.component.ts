import { Component, OnInit } from '@angular/core';
import { SwapiService } from 'src/services/swapi.service';

@Component({
  selector: 'app-swapi',
  templateUrl: './swapi.component.html',
  styleUrls: ['./swapi.component.css']
})
export class SwapiComponent implements OnInit {
  swapiData = {name:'', id:1}
  paramObj = {category:'user', id:1}
  constructor(private swapiService:SwapiService) { }

  ngOnInit(): void {
  }
  makeServiceCall(){
    // we call the service method by subscribing to it
    // remember the api call will be async so subscribing responds when it returns
    // this.typicodeService.getApiData({category:this.category, id:this.id})
    this.swapiService.getSwapiData(this.paramObj)
      .subscribe( (data:any)=>{
        this.swapiData = data
      } )
  }

}
