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
      </style>
      
      <iron-ajax
        auto
        url="http://localhost:8000/playlists"
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
          
        </div>
      </div>
    `;
  }

}

window.customElements.define('all-playlists', AllPlaylists);
