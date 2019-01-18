/**
 * @license
 * Copyright (c) 2016 The Polymer Project Authors. All rights reserved.
 * This code may only be used under the BSD style license found at http://polymer.github.io/LICENSE.txt
 * The complete set of authors may be found at http://polymer.github.io/AUTHORS.txt
 * The complete set of contributors may be found at http://polymer.github.io/CONTRIBUTORS.txt
 * Code distributed by Google as part of the polymer project is also
 * subject to an additional IP rights grant found at http://polymer.github.io/PATENTS.txt
 */

import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './shared-styles.js';

import '@polymer/polymer/lib/elements/dom-repeat.js';
import '@polymer/iron-ajax/iron-ajax.js';

import '@polymer/paper-button/paper-button.js';
import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/paper-item/paper-item.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-icons/av-icons.js';
import '@polymer/iron-form/iron-form.js';

class AllPlaylists extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
        
        a {
          text-decoration: none;
          color: inherit;
        }
      
        .container {
          display: flex;
          flex-direction: column;
        }
                
        .formInput {
          display: flex;
          flex-direction: row;
          align-items:flex-end;
          justify-content: flex-start;
          margin-top: 10px;
        }

        #playlistName, #playlistDescription {
          width: 350px;
        }

      </style>
      
      <iron-ajax
        auto
        id="getPlaylists"
        url="http://localhost:8080/api/v1/details/playlists"  
        handle-as="json"
        content-type='application/json'
        params="{{header}}"
        last-response="{{playlists}}"
        on-response="gotPlaylists">
      </iron-ajax>
      
      <iron-ajax
          id="sendPlaylistForm"
          method="post"
          url="http://localhost:8080/api/v1/playlists/create"
          handle-as="json"
          body='{"title": "{{title}}","description": "{{description}}"}'
          params="{{header}}"
          content-type="application/json"
          on-response="formResponse"
          on-error="handleError">
        </iron-ajax>

      <div class="card">
        <div class="container">
            
          <h1>Playlists</h1>
          <dom-repeat items="{{playlists}}" as="playlist">
            <template>
              <div>
                <a class="playlistLink" href="[[rootPath]]playlist/[[playlist.id]]">
                  <div class="playlistTrack">
                    [[playlist.title]] - [[playlist.description]]
                  </div>
                </a>
              </div>
            </template>
          </dom-repeat>

          <div class="playlist-container">
            <iron-form id="playlistForm">
              <paper-input type="text" name="name" label="Playlist Name" id="playlistName" value="{{title}}" 
              error-message="Please enter a name" required></paper-input>
              <div class="formInput">
                <paper-input type="text" name="description" label="Playlist Description" id="playlistDescription" value="{{description}}" 
                error-message="Please enter a description"></paper-input>
                <paper-icon-button id="submitBtn" on-tap="submitPlaylistForm" icon="icons:add-circle-outline"></paper-icon-button>
              </div>
            </iron-form>
            
          </div>
        </div>
      </div>
    `;
  }

  submitPlaylistForm() {
    if(this.title == "") {
      this.throwEvent('open-dialog-event', {title: 'Playlist', text: 'Please fill in a title'});
    }
    else{
      this.$.sendPlaylistForm.generateRequest();
    }
  }

  formResponse(e,response) {
    console.log("hi" + response.status);
    if(response.status == 200) {
      this.updatePlaylists();
    } else {
      this.throwEvent('open-dialog-event', {title: 'Playlist', text: 'Something went wrong, please try again'});
    }
  }

  updatePlaylists() {
    this.$.getPlaylists.generateRequest();
  }
  
  gotPlaylists(e, response) {
    if(response.status == 200) {
      this.title = "";
      this.description = "";
    }
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

  throwEvent(name, detail){
    this.dispatchEvent(new CustomEvent(name, 
      { 
          detail: detail, 
          bubbles: true,
          composed: true, 
      }
    ));
  }
}

window.customElements.define('all-playlists', AllPlaylists);
