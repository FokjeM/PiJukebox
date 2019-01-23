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

      <!-- value="{{description}}" -->
     
      <iron-form id="playlistForm">
      
        <paper-input type="file" name="file" label="file" id="file"  
            error-message="Please enter a description"></paper-input>

        <paper-icon-button id="submitBtn" on-tap="submitPlaylistForm" icon="icons:add-circle-outline"></paper-icon-button>

      </iron-form>


      <form action="http://localhost:8080/api/v1/upload/upload1" method="post" enctype="multipart/form-data">
        <!-- <input type="text" name="description" value="some text"> -->
        <!-- <input type="text" name="myName"> -->
        <input type="file" name="file">
        <button type="submit">Submit</button>
      </form>

    `;
  }
  
}

window.customElements.define('upload-track', UploadTrack);
