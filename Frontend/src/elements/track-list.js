import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './../shared-styles.js';
import '@polymer/polymer/lib/elements/dom-repeat.js';
import '@polymer/iron-ajax/iron-ajax.js';

import '@polymer/paper-button/paper-button.js';
import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-icons/av-icons.js';

class TrackList extends PolymerElement {
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
        
        .track-info {
          display: flex;
          flex-direction: column;
          align-items: flex-start;
        }
      </style>
      
      <iron-ajax
        auto
        url="http://localhost:8080/api/v1/laptops/"
        handle-as="json"
        last-response="{{response}}">
      </iron-ajax>
      
      <div class="container">
          
        <div class="track-info">
          <h1>Tracks</h1>
            <dom-repeat items="{{response}}" as="track">
              <template>
                <div class="track-info">
                  <div>
                    <paper-icon-button icon="av:play-circle-outline"></paper-icon-button>
                    [[track.id]]: [[track.name]]
                  </div>
                </div>
              </template>
            </dom-repeat>
        </div>
          
      </div>
    `;
  }

  static get properties() {
    return {
      trackId: {
        type: Number
      },
      trackName: {
        type: String
      },
      trackArtist: {
        type: String
      }
    };
  }

}

customElements.define('track-list', TrackList);
