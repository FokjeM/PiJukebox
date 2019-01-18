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
        method="post"
        url="http://localhost:8080/api/v1/player/current"
        handle-as="json"
        params="{{header}}"
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
  // static get properties() {
  //   return {
  //     trackName: {
  //       type: String,
  //     },
  //     trackArtist: {
  //       type: String,
  //     }
  //   };
  // }

}

customElements.define('current-track', CurrentTrack);
