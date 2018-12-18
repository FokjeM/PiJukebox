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
import './elements/current-track.js';
import './elements/track-list.js';
import './elements/track-control.js';
import './elements/volume-control.js';
import './elements/track-info.js';
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-ajax/iron-ajax.js';
import '@polymer/iron-icons/av-icons.js';
import '@polymer/paper-checkbox/paper-checkbox.js';


class SearchTracks extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
        
        track-info {
          padding:8px;
        }

        paper-checkbox {
          --paper-checkbox-size: 15px;
          padding: 0 15px 0 0;
        }
      </style>

      <div class="card">
        <h1>Search</h1>
        <div>
          <paper-input label="Search..." value="{{searchTerm}}"></paper-input>
        </div>

        <div style="padding:10px 0 5px 0;">
          <paper-checkbox on-change="test">Tracks</paper-checkbox>
          <paper-checkbox>Artists</paper-checkbox>
          <paper-checkbox>Albums</paper-checkbox>
          <paper-checkbox>Playlists</paper-checkbox>
        </div>
        
      </div>


      <!-- Track search ajax -->
      <iron-ajax
        id="ajaxSearchTrack"
        {{ajaxauto}}
        url="http://localhost:8080/test/test/{{searchTerm}}"
        handle-as="json"
        last-response="{{trackResults}}">
      </iron-ajax>



      <!-- Track search results -->
      <div class="card">
        <h1>Tracks</h1>
        <dom-repeat items="{{trackResults}}" as="track">
          <template>
            <div style="display:flex;">
              <paper-icon-button icon="av:queue"></paper-icon-button>
              <track-info
                  track-id="{{track.id}}"
                  track-name="{{track.title}}"
                  track-artist="{{track.artist}}">
              </track-info>
            </div>
          </template>
        </dom-repeat>
      </div>
      
    `;
  }

  test(e){
    alert('test');
    this.$.ajaxSearchTrack.setAttribute('auto', 'hahahahahaa');
    this.$.ajaxSearchTrack.generateRequest();
    this.ajaxauto = "auto";
  }

  static get properties(){
    return {
      ajaxauto: {
        type: String,
        value: ""
      }
    };
  }
}

window.customElements.define('search-tracks', SearchTracks);
