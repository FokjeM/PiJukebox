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

class PagePlaylists extends PolymerElement {
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

        .playlistTrack{
          color: var(--app-primary-color);
          padding: 5px 0;
        }

        paper-icon-button {
          color: var(--app-primary-color);
        }

        #playlistName, #playlistDescription {
          width: 350px;
        }

      </style>

      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>
      
      <!-- Get current playlists -->
      <iron-ajax
        auto
        id="getPlaylists"
        url="[[apiRootPath]]/details/playlists"  
        handle-as="json"
        content-type='application/json'
        params="{{header}}"
        last-response="{{playlists}}">
      </iron-ajax>
      
      <!-- Create new playlist -->
      <iron-ajax
        id="sendPlaylistForm"
        method="post"
        url="[[apiRootPath]]/playlists/create"
        handle-as="json"
        body='{"title": "{{title}}","description": "{{description}}"}'
        params="{{header}}"
        content-type="application/json"
        on-response="formResponse"
        on-error="formResponseError">
      </iron-ajax>

      <div class="card">
        <div class="container">
          <h1>Playlists</h1>
          <dom-repeat items="{{playlists}}" as="playlist">
            <template>
              <div>
                <a class="playlistLink" href="[[rootPath]]playlist/[[playlist.id]]">
                  <div class="playlistTrack">
                    [[playlist.title]]

                    <template is="dom-if" if="[[playlist.description]]">
                      - [[playlist.description]]
                    </template>
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
      this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Status', text: 'Please fill in the title.'}, bubbles: true, composed: true }));
    }
    else{
      this.$.sendPlaylistForm.generateRequest();
    }
  }

  formResponse() {
    this.updatePlaylists();
    this.dispatchEvent(new CustomEvent('refresh-playlists-event', { bubbles: true, composed: true }));
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Status', text: 'Playlist created.'}, bubbles: true, composed: true }));
    this.clearPlaylistForm();
  }

  formResponseError(){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Status', text: 'Could not create playlist.'}, bubbles: true, composed: true }));
  }

  updatePlaylists() {
    this.$.getPlaylists.generateRequest();
  }
  
  clearPlaylistForm() {
      this.title = "";
      this.description = "";
  }

  static get properties() {
    return {
      playlists: {
        type: Object
      },
      title: {
        type: String
      },
      description: {
        type: String
      },
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

window.customElements.define('page-playlists', PagePlaylists);
