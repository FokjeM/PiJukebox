import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './shared-styles.js';
import '@polymer/polymer/lib/elements/dom-repeat.js';
import '@polymer/iron-ajax/iron-ajax.js';

import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';

class TrackQueue extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }

        .queueItem {
          display: flex;
          flex-direction: row;
          justify-content: flex-start;
          align-items: center;
          background-color: #00000005;
          margin-bottom: 15px;
          padding: 10px;
        }
        
        .trackLink {
          display: flex;
          flex-direction: column;
          width: 100%;
        }

        .trackArtist {
          font-size: 12px;
        }
        
      </style>
      
      <iron-ajax
        auto
        url="http://localhost:8080/api/v1/laptops/"
        handle-as="json"
        last-response="{{response}}">
      </iron-ajax>

      <div class="card">  
        <div class="container">
          
          <h1>Queue</h1>
          <dom-repeat items="{{response}}" as="track">
            <template>
              <div on-track="handleTrack()" class="queueItem">
                <div class="controls">
                  <paper-icon-button icon="arrow-upward"></paper-icon-button>
                  <paper-icon-button icon="arrow-downward"></paper-icon-button>
                </div>
                <div class="trackLink">
                  <div class="trackName">
                    [[track.name]]
                  </div>
                  <div class="trackArtist">
                    artist
                  </div>
                </div>
              </div>
            </template>
          </dom-repeat>
          
        </div>
      </div>

    `;
  }

  handleTrack(e) {
    console.log('tracking');
  }

  // static get properties() {
  //   return {
  //     trackId: {
  //       type: Number
  //     },
  //     trackName: {
  //       type: String
  //     },
  //     trackArtist: {
  //       type: String
  //     }
  //   };
  // }

}

customElements.define('track-queue', TrackQueue);
