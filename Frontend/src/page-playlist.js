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

class PagePlaylist extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
        #addPlaylistToQueue {
          font-size:14px;
          color: var(--app-primary-color);
        }
        #addPlaylistToQueue:hover {
          cursor: pointer;
        }
      </style>
      
      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>

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
        id="getDetails"
        url="[[apiRootPath]]/details/playlists/[[routeData.playlistId]]"
        handle-as="json"
        params="{{header}}"
        last-response="{{playlist}}">
      </iron-ajax>

      <iron-ajax
        id="addPlaylist"
        method="post"
        url="[[apiRootPath]]/player/add/playlist/[[routeData.playlistId]]"
        handle-as="json"
        params="{{header}}"
        on-response="handleAddPlaylist"
        on-error="handleAddPlaylistError">
      </iron-ajax>

      <div class="card">
        <h1>[[playlist.title]]</h1>
        <p><i>[[playlist.description]]</i></p>
        <template is="dom-if" if="{{playlistTrackCount}}">
          <paper-button id="addPlaylistToQueue" on-click="addPlaylistToQueue">Add playlist to Queue</paper-button>
        </template>
      </div>

      <!-- Artist tracks -->
      <div id="artistTracks" class="card">
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

  ready(){
    super.ready();
    window.addEventListener('refresh-playlist-event', function() {
      this.$.getDetails.generateRequest();
    }.bind(this));
  }

  addPlaylistToQueue(){
    this.$.addPlaylist.generateRequest();
  }

  handleAddPlaylist(){
    this.dispatchEvent(new CustomEvent('refresh-queue-event', { bubbles: true, composed: true }));
    this.dispatchEvent(new CustomEvent('refresh-track-control-event', { bubbles: true, composed: true }));
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Playlist', text: 'Playlist has been added to the queue.'}, bubbles: true, composed: true }));
  }

  handleAddPlaylistError(){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Playlist', text: 'Playlist has not been added to the queue.'}, bubbles: true, composed: true }));
  }

  static get properties() {
    return {
      playlist: {
        type: Object
      },
      playlistTrackCount: {
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
}

window.customElements.define('page-playlist', PagePlaylist);
