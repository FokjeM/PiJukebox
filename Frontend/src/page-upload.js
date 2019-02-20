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

class PageUpload extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }

        #fileUpload {
          max-width: 350px;
        }

        .uploadForm {
          display: flex;
          flex-direction: row;
          align-items:flex-end;
          justify-content: space-between;
          margin-top: 10px;
          max-width: 500px;
        }

        #scanForFilesContainer {
          margin-top: 100px;
        }

        paper-button {
          color: var(--app-primary-color);
        }
      </style>
      
      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>

      <iron-ajax
        id="scanFolder"
        method="post"
        url="[[apiRootPath]]/upload/folder/"
        params="{{header}}"
        handle-as="json"
        on-response="handleScanResponse"
        on-error="handleError">
      </iron-ajax>

      <iron-request
        id="sendUploadForm"
        handle-as="document">
      </iron-request>

      <div class="card">  
        <div class="container">
          <form class="uploadForm">
            <input id="fileUpload" type="file" name="file" multiple>
            <paper-button raised id="subBtn" on-tap="subForm">Send</paper-button>
          </form>
        </div>

        <div class="container" id="scanForFilesContainer">
          <paper-button raised id="subBtn" on-tap="scanForFiles">Scan folder for files</paper-button>
        </div>
      </div>
    `;
  }

  subForm() {
    const files = this.$.fileUpload.files;
    let data = new FormData();

    for(let i=0; i < files.length; i++) {
      data.append("file", this.$.fileUpload.files[i]);
    }
    
    let xhr = new XMLHttpRequest();

    xhr.addEventListener('load', function() {
      if(xhr.status === 409){
        this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Upload', text: 'File already exists.'}, bubbles: true,composed: true }));
      }
      else if(xhr.status === 201){
        this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Upload', text: 'File has been uploaded.'}, bubbles: true,composed: true }));
      }
      else{
        this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Upload', text: 'Something went wrong.'}, bubbles: true,composed: true }));
      }
    }.bind(this));
    
    xhr.Authorization = true;
    xhr.open("POST", this.apiRootPath+"/upload?Authorization=" + JSON.parse(JSON.stringify(this.header)).Authorization);
    xhr.send(data);
  }

  scanForFiles() {
    this.$.scanFolder.generateRequest();
  }

  handleScanResponse(){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Upload', text: 'Scan was successful'}, bubbles: true, composed: true }));
  }

  handleError(e,r){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Upload', text: 'Something went wrong'}, bubbles: true,composed: true }));
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

window.customElements.define('page-upload', PageUpload);