import { Component,NgModule } from '@angular/core';
import {AuthenticationRequest} from '../../services/models/authentication-request';
import { CommonModule,} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthenticationControllerService } from '../../services/services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  constructor(private router:Router,private authService:AuthenticationControllerService,){}
register() {
this.router.navigate(['register'])
}
login() {
this.errorMsg = [];
this.authService.authenticate({
  body:this.authRequest
}).subscribe({
  next:() =>{
    // save the token
    this.router.navigate(["books"]);
  },
  error:(err)=>{
    console.log(err)
  }
});
}
authRequest: AuthenticationRequest ={email:'',password:''}
errorMsg : Array<string> = [];

}
