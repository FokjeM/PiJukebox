import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './../shared-styles.js';

import '@polymer/polymer/lib/elements/dom-repeat.js';
import '@polymer/iron-ajax/iron-ajax.js';

import './playlist-track-row.js';

class TrackList extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }
      </style>
      
      <iron-ajax
        auto
        url="http://localhost:8080/api/v1/extended/tracks"
        handle-as="json"
        last-response="{{response}}">
      </iron-ajax>
      
      <div class="container">
        
        <h1>Tracks</h1>
        <dom-repeat items="{{response}}" as="track">
          <template>
            <playlist-track-row 
                track="[[track]]">
            </playlist-track-row>
          </template>
        </dom-repeat>
        
      </div>
    `;
  }

}

customElements.define('track-list', TrackList);
