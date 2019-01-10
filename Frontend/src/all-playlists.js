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
        
        .playlist-form {
          display: flex;
          flex-direction: row;
          margin-top: 10px;
        }
      </style>
      
      <iron-ajax
        auto
        url="http://localhost:8080/api/v1/laptops"
        handle-as="json"
        last-response="{{response}}">
      </iron-ajax>
        
      <div class="card">
        <div class="container">
            
          <h1>Playlists</h1>
          <dom-repeat items="{{response}}" as="playlist">
            <template>
              <div>
                <a class="playlistLink" href="[[rootPath]]playlist/[[playlist.id]]">
                  <div class="playlistTrack">
                    [[playlist.id]]: [[playlist.name]]
                  </div>
                </a>
              </div>
            </template>
          </dom-repeat>

          <div class="playlist-form">
            <iron-form id="playlistForm">
              <!-- <form action="http://localhost:8000/playlists/create" method="get"> -->
              <form action="http://localhost:8000/playlists/create" method="post">
                <paper-input type="text" name="name" label="Name" id="playlistName" 
                            error-message="Please enter a name" required></paper-input>
                <paper-icon-button on-tap="_submitForm" icon="icons:add-circle-outline"></paper-icon-button>
                <!-- <paper-button raised on-tap="_submitForm">Add new playlist</paper-button> -->
              </form>
            </iron-form>
            
          </div>
        </div>
      </div>
    `;
  }

  _submitForm() {
    let pForm = this.$.playlistForm;

    let isValid = pForm.validate();
    if(isValid) {
      this.$.playlistForm.submit();
      console.log("submitted");
    }
  }

}

window.customElements.define('all-playlists', AllPlaylists);
