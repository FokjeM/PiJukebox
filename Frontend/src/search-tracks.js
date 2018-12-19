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
          <paper-checkbox data-ajax="ajaxSearchTrack" data-results="trackResults" on-change="setAjaxAuto">Tracks</paper-checkbox>
          <paper-checkbox data-ajax="ajaxSearchArtist" data-results="artistResults" on-change="setAjaxAuto">Artists</paper-checkbox>
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
      <div id="trackResults" class="card" hidden>
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

      <!-- Artist search ajax -->
      <iron-ajax
        id="ajaxSearchArtist"
        {{ajaxauto}}
        url="http://localhost:8080/test/artist/{{searchTerm}}"
        handle-as="json"
        last-response="{{artistResults}}">
      </iron-ajax>
      
      <!-- Artist search results -->
      <div id="artistResults" class="card" hidden>
        <h1>Artists</h1>
        <dom-repeat items="{{artistResults}}" as="artist">
          <template>
            <div style="display:flex;">
              <paper-icon-button icon="av:queue"></paper-icon-button>
              <track-info
                  track-id="{{artist.id}}"
                  track-name="{{artist.title}}"
                  track-artist="{{artist.artist}}">
              </track-info>
            </div>
          </template>
        </dom-repeat>
      </div>
      
    `;
  }

  //Toggles ajax auto attribute and hide/show results according to checkbox value
  setAjaxAuto(e){
    //Select iron ajax ID by checkbox data-ajax || data-ajax == iron ajax id
    var ajaxElement = this.shadowRoot.getElementById(e.target.dataset.ajax);
    //Select result div ID by checkbox data-results || data-results == result div id
    var ajaxResults = this.shadowRoot.getElementById(e.target.dataset.results);

    if(e.target.checked){
      ajaxElement.setAttribute('auto', '');
      ajaxResults.hidden = false;
    }
    else{
      ajaxElement.removeAttribute('auto');
      ajaxResults.hidden = true;
    }
  }
}

window.customElements.define('search-tracks', SearchTracks);
