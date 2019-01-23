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
import '@polymer/iron-form/iron-form.js';
import '@polymer/paper-button/paper-button.js';
import './elements/result-row-track.js';

class UploadTrack extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
      </style>
      
      <iron-request
        id="sendUploadForm"
        handle-as="document">
      </iron-request>
      
      <form>
        <input id="fileUpload" type="file" name="file" multiple>
        <paper-button id="subBtn" on-tap="SubForm">Send</paper-button>
      </form>

    `;
  }

  SubForm() {
    let files = this.$.fileUpload.files;
    let data = new FormData();

    for(let i=0; i < files.length; i++) {
      data.append("file", this.$.fileUpload.files[i]);
    }
    
    let xhr = new XMLHttpRequest();
    xhr.Authorization = true;
    xhr.open("POST", "http://localhost:8080/api/v1/upload?Authorization=" + JSON.parse(JSON.stringify(this.header)).Authorization);
    xhr.send(data);
  }

  static get properties() {
    return {
      token: {
        type: String,
        value: localStorage.getItem("token")
      },
      header: {
        type: Object,
        reflectToAttribute: true,
        computed: '_computeTokenHeaders(token)'
      }
    };
  }
  _computeTokenHeaders(token) {
    return {
      'Authorization': token
    };
  }
}

window.customElements.define('upload-track', UploadTrack);