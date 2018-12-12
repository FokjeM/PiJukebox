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

class MyView1 extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
        
        current-track {
          width: 100%;
        }
      </style>

      <div class="card">
        <track-list></track-list>
      </div>
      
      <div class="card">
        <current-track
            track-id="1"
            track-name="The current track"
            track-artist="Artist of the track">
        </current-track>
        <track-control></track-control>
        <volume-control></volume-control>
      </div>
    `;
  }
}

window.customElements.define('my-view1', MyView1);
