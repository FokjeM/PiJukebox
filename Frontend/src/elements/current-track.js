import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '@polymer/iron-ajax/iron-ajax.js';

import './../shared-styles.js';

class CurrentTrack extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }
        
        .container {
          display: flex;
          flex-direction: column;
        }
        
        .trackInfo {
          display: flex;
          flex-direction: column;
          align-items: center;
        }
      </style>

      <iron-ajax
        auto
        url="http://localhost:8080/api/v1/laptops/1/"
        handle-as="json"
        last-response="{{currentTrack}}">
      </iron-ajax>

      <div class="container">
        
        <div class="trackInfo">
          <p>[[currentTrack.trackName]]</p>
          <p>[[currentTrack.trackArtist]]</p>
        </div>

      </div>
    `;
  }

}

customElements.define('current-track', CurrentTrack);
