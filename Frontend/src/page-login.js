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

class PageLogin extends PolymerElement {
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
      
      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>

      <div>
        <div style="padding:20px; 40px 20px 20px;">
          <paper-input label="Email" value="{{email}}" autofocus></paper-input>
          <paper-input type="password" label="Password" value="{{password}}"></paper-input>
          <paper-button id="loginButton" style="background-color: #00796B; color: white; width:100%; margin-top:20px;" raised on-tap="submitLogin">Login</paper-button>
        </div>

        <!-- Post credentials -->
        <iron-ajax
          id="loginForm"
          method="post"
          url="[[apiRootPath]]/login"
          handle-as="json"
          body='{"email": "{{email}}", "password": "{{password}}"}'
          content-type='application/json'
          on-response="setToken"
          on-error="handleError">
        </iron-ajax>
      </div>

    `;
  }

  submitLogin(){
    this.$.loginForm.generateRequest();
  }

  setToken(e,r){
    //Store token in local storage
    var token = r.response.token;
    window.localStorage.setItem("token", token);

    //Redirect to /search
    window.history.pushState({}, null, '/search');
    window.dispatchEvent(new CustomEvent('location-changed'));
  }

  handleError(){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Login', text: 'Login failed (Invalid credentials?)'}, bubbles: true,composed: true, }));
  }

  static get properties() {
    return {
      email: {
        type: String
      },
      password: {
        type: String
      }
    };
  }

  
}

window.customElements.define('page-login', PageLogin);
