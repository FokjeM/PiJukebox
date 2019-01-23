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

class SinglePlaylist extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }

        #addPlaylistToQueue {
          background-color:#44c767;
          -moz-border-radius:28px;
          -webkit-border-radius:28px;
          border-radius:28px;
          border:1px solid #18ab29;
          display:inline-block;
          cursor:pointer;
          color:#ffffff;
          font-family:Arial;
          font-size:17px;
          padding:16px 31px;
          text-decoration:none;
          text-shadow:0px 1px 0px #2f6627;
        }
        #addPlaylistToQueue:hover {
          background-color:#5cbf2a;
        }
        #addPlaylistToQueue:active {
          position:relative;
          top:1px;
        }
      </style>
      
      <app-location 
        route="{{route}}"
        url-space-regex="^[[rootPath]]">
      </app-location>

      <app-route 
        route="{{route}}" 
        pattern="[[rootPath]]playlist/:playlistId"
        data="{{routeData}}" 
        tail="{{subroute}}">
      </app-route>

      <!-- Get all playlist info -->
      <iron-ajax
        auto
        url="http://localhost:8080/api/v1/details/playlists/[[routeData.playlistId]]"
        handle-as="json"
        params="{{header}}"
        last-response="{{playlist}}">
      </iron-ajax>

      <div class="card">
        <h1>[[playlist.title]]</h1>
        <p><i>[[playlist.description]]</i></p>
      </div>

      <!-- Artist tracks -->
      <div id="artistTracks" class="card">
      <button id="addPlaylistToQueue">Playlist to Queue</button>
      <h1>Tracks</h1> 

        <template is="dom-repeat" items="{{playlist.tracks}}" as="track" rendered-item-count="{{playlistTrackCount}}">
          <div style="display:flex;">
              <result-row-track
                  track-id="{{track.id}}"
                  track-name="{{track.name}}"
                  exclude-artist="true">
                  <!-- track-artist="{{track.artists}}" -->
              </result-row-track>
            </div>
        </template>

        <template is="dom-if" if="{{!playlistTrackCount}}">
          No tracks.
        </template> 
      </div>
    `;
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

window.customElements.define('single-playlist', SinglePlaylist);
