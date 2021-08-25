import { Injectable } from '@angular/core';
// I want to use http in this service
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class SwapiService {
  constructor(private http:HttpClient) { }
  getSwapiData(params={category:'people',id:1}){
    return this.http.get(`https://swapi.dev/api/${params.category}/${params.id}`)
  }

}
