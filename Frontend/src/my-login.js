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

class MyLogin extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
      </style>
      
      <div>
        <div>
          <paper-input label="Username" value="{{username}}"></paper-input>
          <paper-input label="Password" value="{{password}}"></paper-input>
          <button on-click="submitLogin">Login</button>
        </div>

        <!-- Post credentials -->
        <iron-ajax
          id="loginForm"
          url="http://localhost:8000/login"
          handle-as="json"
          body="{username: {{username}}, password: {{password}}}"
          on-response="setToken">
        </iron-ajax>
      </div>

    `;
  }

  submitLogin(){
    this.$.loginForm.generateRequest();
  }

  setToken(e,r){
    if(r.status == 200){
      localStorage.setItem("token", r.response.token);
      window.location.href = "/";
    }
    else{
      this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Login', text: 'Invalid credentials'}, bubbles: true,composed: true, }));
    }
  }
}

window.customElements.define('my-login', MyLogin);
