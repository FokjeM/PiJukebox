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
        
        .track-link {
          display: flex;
          flex-direction: row;
          justify-content: space-between;
          justify-items: center;
          padding: 10px;
        }
        
        .track-link:hover{
          background-color: #eeeeee;
          border-radius: 50px;
        }
        
        .track-track{
          text-align: left;
          padding-top: 9px;
        }
        
        .track-artist {
          text-align: center;
          padding-top: 9px;
        }
        
        .track-time {
          text-align: right;
          padding-top: 9px;
        }
        
        .track-link div{
          width: 25%;
        }
      </style>
      
      <iron-ajax
        auto
        url="http://localhost:8080/api/v1/laptops/"
        handle-as="json"
        last-response="{{response}}">
      </iron-ajax>
      
      <div class="container">
        
        <h1>Tracks</h1>
        <dom-repeat items="{{response}}" as="track">
          <template>
            <div class="track-link">
              <div class="play-icon">
                <paper-icon-button icon="av:play-circle-outline"></paper-icon-button>
              </div>
              <div class="track-track">
                [[track.id]]: [[track.name]]
              </div>
              <div class="track-artist">
                artist
              </div>
              <div class="track-time">
                time
              </div>
            </div>
          </template>
        </dom-repeat>
        
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
