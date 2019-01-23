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
import './elements/result-row-track.js';
import '@polymer/iron-form/iron-form.js';
import '@polymer/paper-button/paper-button.js';

class UploadTrack extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
      </style>
      
      <iron-ajax
        id="sendPlaylistForm"
        method="post"
        url="http://localhost:8080/api/v1/upload/add"
        handle-as="json"
        body='{"title": "{{title}}","description": "{{description}}"}'
        params="{{header}}"
        content-type="application/json"
        on-response="formResponse"
        on-error="handleError">
      </iron-ajax>

      <iron-request
        id="sendUploadForm"
        handle-as="document"
        on-response="formResponse">
      </iron-request>
      <!-- body='{"title": "{{title}}","description": "{{description}}"}' -->
     
      <iron-form id="playlistForm">
        <paper-input type="file" name="file" label="file" id="file"  
            error-message="Please enter a description"></paper-input>
        <paper-icon-button id="submitBtn" on-tap="submitPlaylistForm" icon="icons:add-circle-outline"></paper-icon-button>
      </iron-form>


      <form id="myForm" action="http://localhost:8080/api/v1/upload" method="post" enctype="multipart/form-data">
        <input id="fileUpload" type="file" name="file" value="{{thefile}}">
        <!-- <button type="submit">Submit</button> -->
        <paper-button id="subBtn" on-tap="SubForm">Send</paper-button>
        <!-- <button on-tap="SubForm">Submit</button> -->
      </form>

    `;
  }

  SubForm() {
    console.log(this.$.fileUpload);
    console.log(this.$.fileUpload.name);
    console.log(this.$.fileUpload.type);
    console.log(this.$.fileUpload.size);
    console.log(this.$.fileUpload.files[0]);
    var data = new FormData();
    data.append("file", this.$.fileUpload.files[0]);
    // data.append("url","http://localhost:8080/api/v1/upload");
    console.log("HI");
    // this.$.sendUploadForm.open("POST", "http://localhost:8080/api/v1/upload");
    // this.$.sendUploadForm.open();
    // this.$.sendUploadForm.setRequestHeader("Content-Type", "multipart/form-data");
    var xhr = new XMLHttpRequest();
    xhr.Authorization = true;
    
    console.log();
    xhr.open("POST", "http://localhost:8080/api/v1/upload?Authorization="+JSON.parse(JSON.stringify(this.header)).Authorization);
    
    // xhr.setRequestHeader("Content-Type", "multipart/form-data");
    xhr.send(data);
    // this.$.sendUploadForm.send({ 
    //   url: "http://localhost:8080/api/v1/upload",
    //   headers: {
    //     "Content-Type": "multipart/form-data"
    //   },
    //   params: "[[header]]",
    //   body: this.$.fileUpload.files[0],
    //   method: "post"
    // });
    console.log(this.$.sendUploadForm);
    
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
  _computeTokenHeaders(token)
  {
      return {'Authorization': token};
  }
}

window.customElements.define('upload-track', UploadTrack);
