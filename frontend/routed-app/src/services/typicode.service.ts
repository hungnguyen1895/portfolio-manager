import { Injectable } from '@angular/core';
// I want to use http in this service
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class TypicodeService {
  // this is classic how-to for http
  constructor(private http:HttpClient) { }
  // we need a method of this service - in this case we call an API end-point
  getApiData(params={category:'users',id:1}){ // all httpClient services are OBSERVABLES

    return this.http.get(`https://jsonplaceholder.typicode.com/${params.category}/${params.id}`)
  }

}
