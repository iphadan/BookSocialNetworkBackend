import { Component,NgModule } from '@angular/core';
import {AuthenticationRequest} from '../../services/models/authentication-request';
import { CommonModule,} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthenticationControllerService } from '../../services/services';
import { Router } from '@angular/router';
import { AuthenticationResponse, RegistrationRequest } from '../../services/models';
import { ColdObservable } from 'rxjs/internal/testing/ColdObservable';
import { TokenService } from '../../services/services/token/token.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
register() {
  this.router.navigate(['/register']);
}
  constructor(private router:Router,
    private authService:AuthenticationControllerService,
  private tokenService : TokenService
  ){}
authRequest: AuthenticationRequest ={email:'',password:''};
errorMsg : Array<string> = [];


login() {
this.errorMsg = [];
this.authService.authenticate({
  body:this.authRequest
}).subscribe({
  next:(res:AuthenticationResponse):void =>{
    this.tokenService.token = res.token as string;
    console.log(res.token)
    this.router.navigate(["books"])
  },
  error:(err):void =>{
    if(err.error.listValidationErrors ){
      this.errorMsg = (err.error.listValidationErrors)
}
    else{
      this.errorMsg.push(err.error.error)

    }
  }
});
}


}
