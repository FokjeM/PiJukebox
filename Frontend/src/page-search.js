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
import './elements/track-control.js';
import './elements/result-row-track.js';
import './elements/result-row-artist.js';
import './elements/result-row-playlist.js';
import './elements/result-row-album.js';
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-ajax/iron-ajax.js';
import '@polymer/iron-icons/av-icons.js';
import '@polymer/paper-checkbox/paper-checkbox.js';
import '@polymer/iron-meta/iron-meta.js';



class PageSearch extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
          padding: 10px;
          --paper-checkbox-unchecked-background-color: var(--app-primary-color);
          --paper-checkbox-checked-color: var(--app-primary-color);
          --paper-checkbox-unchecked-color: var(--app-primary-color);
        }
      
        paper-checkbox {
          --paper-checkbox-size: 15px;
          padding: 0 15px 0 0;
        }
      </style>

      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>

      <div class="card">
        <h1>Search</h1>
        <div>
          <paper-input label="Search..." value="{{searchTerm}}"></paper-input>
        </div>

        <div style="padding:10px 0 5px 0;">
          <paper-checkbox checked data-ajax="ajaxSearchTrack" data-results="trackResults" on-change="setAjaxAuto">Tracks</paper-checkbox>
          <paper-checkbox data-ajax="ajaxSearchArtist" data-results="artistResults" on-change="setAjaxAuto">Artists</paper-checkbox>
          <paper-checkbox data-ajax="ajaxSearchAlbum" data-results="albumResults" on-change="setAjaxAuto">Albums</paper-checkbox>
          <paper-checkbox data-ajax="ajaxSearchPlaylist" data-results="playlistResults" on-change="setAjaxAuto">Playlists</paper-checkbox>
        </div>
      </div>

      <!-- Track search ajax -->
      <iron-ajax
      method="GET"
        auto
        id="ajaxSearchTrack"
        url="[[apiRootPath]]/extended/tracks?name={{searchTerm}}"
        handle-as="json"
        params="{{header}}"
        content-type="application/json"
        last-response="{{trackResults}}">
      </iron-ajax>

      <!-- Track search results -->
      <div id="trackResults" class="card">
        <h1>Tracks</h1>

        <template is="dom-repeat" items="{{trackResults}}" as="track" rendered-item-count="{{trackResultCount}}">
          <div style="display:flex;">          
              <result-row-track
                  track-id="{{track.id}}"
                  track-name="{{track.name}}"
                  track-artist="{{track.artists}}">
              </result-row-track>
            </div>
        </template>

        <template is="dom-if" if="{{!trackResultCount}}">
          No results.
        </template> 
      </div>

      <!-- Artist search ajax -->
      <iron-ajax
        id="ajaxSearchArtist"
        url="[[apiRootPath]]/simple/artists?name={{searchTerm}}"
        handle-as="json"
        params="{{header}}"
        last-response="{{artistResults}}">
      </iron-ajax>
  
      <!-- Artist search results -->
      <div id="artistResults" class="card" hidden>
        <h1>Artists</h1>

        <template is="dom-repeat" items="{{artistResults}}" as="artist" rendered-item-count="{{artistResultCount}}">
          <div style="display:flex;">
              <result-row-artist
                  artist-id="{{artist.id}}"
                  artist-name="{{artist.name}}">
              </result-row-artist>
            </div>
        </template>

        <template is="dom-if" if="{{!artistResultCount}}">
          No results.
        </template> 
      </div>

      <!-- Album search ajax -->
      <iron-ajax
        id="ajaxSearchAlbum"
        url="[[apiRootPath]]/simple/albums?name={{searchTerm}}"
        handle-as="json"
        params="{{header}}"
        last-response="{{albumResults}}">
      </iron-ajax>

      <!-- Album search results -->
      <div id="albumResults" class="card" hidden>
        <h1>Albums</h1>

        <template is="dom-repeat" items="{{albumResults}}" as="album" rendered-item-count="{{albumResultCount}}">
          <div style="display:flex;">
              <result-row-album
                album-id="{{album.id}}"
                album-name="{{album.name}}">
              </result-row-album>
            </div>
        </template>

        <template is="dom-if" if="{{!albumResultCount}}">
          No results.
        </template> 
      </div>

      <!-- Playlist search ajax -->
      <iron-ajax
        id="ajaxSearchPlaylist"
        url="[[apiRootPath]]/playlists?name={{searchTerm}}"
        handle-as="json"
        params="{{header}}"
        last-response="{{playlistResults}}">
      </iron-ajax>

      <!-- Playlist search results -->
      <div id="playlistResults" class="card" hidden>
        <h1>Playlists</h1>

        <template is="dom-repeat" items="{{playlistResults}}" as="playlist" rendered-item-count="{{playlistResultCount}}">
          <div style="display:flex;">
              <result-row-playlist
                playlist-id="{{playlist.id}}"
                playlist-name="{{playlist.title}}">
              </result-row-playlist>
            </div>
        </template>

        <template is="dom-if" if="{{!playlistResultCount}}">
          No results.
        </template> 
      </div>

      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>
    
    `;
  }

  static get properties() {
    return {
      searchTerm: {
        type: String
      },
      trackResults: {
        type: Object
      },
      trackResultsCount: {
        type: Number
      },
      albumResults: {
        type: Object
      },
      albumResultCount: {
        type: Number
      },
      playlistResults: {
        type: Object
      },
      playlistResultsCount: {
        type: Number
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

  //Toggle ajax auto attribute and hide/show results according to checkbox value
  setAjaxAuto(e){
    //Select iron ajax ID by checkbox data-ajax || data-ajax == iron ajax id
    let ajaxElement = this.shadowRoot.getElementById(e.target.dataset.ajax);
    //Select result div ID by checkbox data-results || data-results == result div id
    let ajaxResults = this.shadowRoot.getElementById(e.target.dataset.results);

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

window.customElements.define('page-search', PageSearch);
