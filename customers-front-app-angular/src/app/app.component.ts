import {Component, OnInit} from '@angular/core';
import {SecurityService} from "./services/security.service";
import {KeycloakService} from "keycloak-angular";
import {KeycloakProfile} from "keycloak-js";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'front-end-angular';

  public isLoggedIn = false;
  public userProfile: KeycloakProfile | null = null;

  constructor(public readonly keycloak: KeycloakService) {}

  public async ngOnInit() {
    this.isLoggedIn = await this.keycloak.isLoggedIn();

    if (this.isLoggedIn) {
      this.userProfile = await this.keycloak.loadUserProfile();
    }
  }

  async login(){
    await this.keycloak.login({
      redirectUri : window.location.origin
    })
  }

  public logout() {
    this.keycloak.logout(window.location.origin);
  }

  public profile() {
    window.location.href="http://localhost:8080/realms/customers-realm/account/#/personal-info";
  }

  /*
  constructor(public securityService: SecurityService ) {
  }
  async login(){
    await this.securityService.kcService.login({
      redirectUri : window.location.origin
    })
  }
  onLogout() {
    this.securityService.kcService.logout(window.location.origin);
  }

  profile() {
    window.location.href="http://localhost:8080/realms/customers-realm/account/#/personal-info";
  }
  */

}
