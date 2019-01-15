/**
 * @license
 * Copyright (c) 2016 The Polymer Project Authors. All rights reserved.
 * This code may only be used under the BSD style license found at http://polymer.github.io/LICENSE.txt
 * The complete set of authors may be found at http://polymer.github.io/AUTHORS.txt
 * The complete set of contributors may be found at http://polymer.github.io/CONTRIBUTORS.txt
 * Code distributed by Google as part of the polymer project is also
 * subject to an additional IP rights grant found at http://polymer.github.io/PATENTS.txt
 */

import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import './shared-styles.js';

import '@polymer/app-route/app-location.js';
import '@polymer/app-route/app-route.js';
import '@polymer/iron-ajax/iron-ajax.js';
import '@polymer/paper-input/paper-input.js';
import '@polymer/paper-button/paper-button.js';

class MyLogin extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
        #loginButton{
          background-color: #00796B;
          color: white;
          width:100%;
          margin-top:20px;
        }
      </style>
      
      <div>
        <div style="padding:20px; 40px 20px 20px;">
          <paper-input label="Username" value="{{username}}"></paper-input>
          <paper-input label="Password" value="{{password}}"></paper-input>
          <paper-button id="loginButton" raised on-click="submitLogin">Login</paper-button>
        </div>

        <!-- Post credentials -->
        <iron-ajax
          id="loginForm"
          url="http://localhost:8080/api/v1/login"
          handle-as="json"
          body="{username: {{username}}, password: {{password}}}"
          on-response="setToken">
        </iron-ajax>
      </div>

    `;
  }

  submitLogin(){
    localStorage.setItem("token", "r.response.token");
    this.$.loginForm.generateRequest();
  }

  setToken(e,r){
    if(r.status == 200){
      localStorage.setItem("token", r.response.token);
      // window.location.href = "/";
    }
    else{
      this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Login', text: 'Invalid credentials'}, bubbles: true,composed: true, }));
    }
  }
}

window.customElements.define('my-login', MyLogin);