import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  set token(token:string){
    localStorage.setItem('book_token',token);
  }
  get token(){
    return localStorage.getItem('book_token') as string;
  }
}
