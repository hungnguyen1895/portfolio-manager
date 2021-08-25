import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CashFlowComponent } from './cash-flow/cash-flow.component';
import { InsightsComponent } from './insights/insights.component';
import { MarketMoversComponent } from './market-movers/market-movers.component';
import { NetWorthComponent } from './net-worth/net-worth.component';
import { SwapiComponent } from './swapi/swapi.component';

// all routes are inspected in strict order until a match is found
const routes: Routes = [
  {path:'', redirectTo: '/cash-flow', pathMatch:'full'},
  {path:'cash-flow', component:CashFlowComponent},
  {path:'market-movers', component:MarketMoversComponent},
  {path:'net-worth', component:NetWorthComponent},
  // how about alternative spelling/names so users can guess their way around
  {path:'networth', component:NetWorthComponent},
  {path:'insights', component:InsightsComponent},
  {path:'swapi', component:SwapiComponent},
  // we should catch any route that has not already been matched
  {path:'**', component:CashFlowComponent} // put this in only when the other routes work!!
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
