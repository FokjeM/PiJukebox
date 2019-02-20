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


class PageAlbum extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
      </style>
      
      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>

      <app-location 
        route="{{route}}"
        url-space-regex="^[[rootPath]]">
      </app-location>

      <app-route 
        route="{{route}}" 
        pattern="[[rootPath]]album/:albumId"
        data="{{routeData}}" 
        tail="{{subroute}}">
      </app-route>

      <!-- Get all album info -->
      <iron-ajax
        auto
        url="[[apiRootPath]]/extended/albums/[[routeData.albumId]]"
        handle-as="json"
        params="{{header}}"
        last-response="{{album}}">
      </iron-ajax>
      
      <div class="card">
        <h1>[[album.name]]</h1>
        <h1>[[album.artist]]</h1>
      </div>

      <!-- Album tracks -->
      <div id="albumTracks" class="card">
        <h1>Tracks</h1>

        <template is="dom-repeat" items="{{album.tracks}}" as="track" rendered-item-count="{{trackCount}}">
          <div style="display:flex;">          
              <result-row-track
                  track-id="{{track.id}}"
                  track-name="{{track.name}}"
                  track-artist="{{album.artists}}">
              </result-row-track>
            </div>
        </template>

        <template is="dom-if" if="{{!trackCount}}">
          No tracks.
        </template> 
      </div>
    `;
  }

  static get properties() {
    return {
      track : {
        type: Object
      },
      trackCount: {
        type: Number
      },
      album : {
        type: Object
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

window.customElements.define('page-album', PageAlbum);
