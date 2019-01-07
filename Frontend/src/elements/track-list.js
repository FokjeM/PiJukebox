import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './../shared-styles.js';
import '@polymer/polymer/lib/elements/dom-repeat.js';
import '@polymer/iron-ajax/iron-ajax.js';

class TrackList extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }

        .trackLink {
          display: flex;
          flex-direction: column;
          margin-bottom: 10px;
        }
        
        .trackInfo {
          display: flex;
          flex-direction: row;
          justify-content: space-between;
          flex-wrap: wrap;
          padding-left: 25px;
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
            <div class="trackLink">
              <div class="trackName">
                [[track.name]]
              </div>
              <div class="trackInfo">
                <div class="trackArtist">
                  artist
                </div>
                <div class="trackTime">
                  time
                </div>
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
