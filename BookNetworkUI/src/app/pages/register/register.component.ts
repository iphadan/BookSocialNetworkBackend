import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationControllerService } from '../../services/services';
import { TokenService } from '../../services/services/token/token.service';
import { RegistrationRequest } from '../../services/models';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
login() {
this.router.navigate(['/login'])}
    constructor(private router:Router,
      private authService:AuthenticationControllerService,
    ){}
  resgisterReqeust: RegistrationRequest = {firstName:'',lastName: '',email :'',password:'',gender:''};
  errorMsg : Array<string> = [];
  register() {
    this.errorMsg = [];
    this.authService.registerUser({body : this.resgisterReqeust}).subscribe({
     
      next:(res : any):void =>{
        this.router.navigate(['/activateAccount'])
      },
      error:(err : any):void =>{
        console.log("error",err.error)
        if(err.error.listValidationErrors){
          this.errorMsg = err.error.listValidationErrors;
        }
      }
    })
  }
}
