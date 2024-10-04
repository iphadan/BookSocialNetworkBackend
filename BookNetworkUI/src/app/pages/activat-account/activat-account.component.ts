import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {  CodeInputModule } from 'angular-code-input';
import { AuthenticationControllerService } from '../../services/services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-activat-account',
  standalone: true,
  imports: [CommonModule,FormsModule,CodeInputModule],
  templateUrl: './activat-account.component.html',
  styleUrl: './activat-account.component.scss'
})
export class ActivatAccountComponent {
login() {

this.router.navigate(['/login'])}
  constructor(private router:Router,
    private authService:AuthenticationControllerService,
  ){}



  message: string ='';
  submitted:boolean=false;
  isOkay:boolean=true;
  
activate(token:string) {
this.confirmAccount(token)
}
  confirmAccount(token: string) {
    this.authService.confirm({token}).subscribe({
      next: (res) =>{
this.message = "Your Account has been Activated";
this.submitted=true;
this.isOkay=true;
      },error:(err) =>{
this.message = "Token has been Expired or Invalid";
this.submitted = true;
this.isOkay = false;
      }
    })
  }

}
