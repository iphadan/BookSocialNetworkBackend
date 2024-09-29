import { Component,NgModule } from '@angular/core';
import {AuthenticationRequest} from '../../services/models/authentication-request';
import { CommonModule,} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthenticationControllerService } from '../../services/services';
import { Router } from '@angular/router';
import { AuthenticationResponse } from '../../services/models';
import { ColdObservable } from 'rxjs/internal/testing/ColdObservable';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  constructor(private router:Router,private authService:AuthenticationControllerService,){}
authRequest: AuthenticationRequest ={email:'',password:''}
errorMsg : Array<string> = [];
register() {
this.router.navigate(['register'])
}
login() {
this.errorMsg = [];
this.authService.authenticate({
  body:this.authRequest
}).subscribe({
  next:(res:AuthenticationResponse):void =>{
    this.router.navigate(["books"])
  },
  error:(err):void =>{
    console.log(err.error)
    if(err.error.listValidationErrors){
      console.log("here")
    }
    else{
      console.log("there")
    }
  }
});
}


}
