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
import './elements/track-row.js';

class TracksPage extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
      </style>

      <iron-ajax
        auto
        url="http://localhost:8080/api/v1/extended/tracks"
        handle-as="json"
        params="{{header}}"
        last-response="{{response}}">
      </iron-ajax>

      <div class="card">      
      
        <div class="container">        
          <h1>Tracks</h1>
          <dom-repeat items="{{response}}" as="track">
            <template>
              <track-row 
                  track="[[track]]">
              </track-row>
            </template>
          </dom-repeat>
        </div>
      
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

window.customElements.define('tracks-page', TracksPage);
