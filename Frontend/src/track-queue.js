import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import {GestureEventListeners} from '@polymer/polymer/lib/mixins/gesture-event-listeners';
import './shared-styles.js';
import '@polymer/polymer/lib/elements/dom-repeat.js';
import '@polymer/iron-ajax/iron-ajax.js';

import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import './elements/sortable-list.js';

class TrackQueue extends GestureEventListeners(PolymerElement) {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }

        .trackLink {
          background-color: #00000005;
          margin-bottom: 15px;
          padding: 10px;
          width: 100%;
          cursor: move;
          -webkit-user-select: none; /* Safari 3.1+ */
          -moz-user-select: none; /* Firefox 2+ */
          -ms-user-select: none; /* IE 10+ */
          user-select: none; /* Standard syntax */
        }

        .trackArtist {
          font-size: 12px;
        }
        
        .item {
        background: #ddd;
        display: inline-block;
        float: left;
        height: 100px;
        margin: 10px 10px 0 0;
        text-align: center;
        vertical-align: top;
        width: 150px;
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

          <sortable-list sortable=".item">
            <dom-repeat items="{{response}}" id="domRepeat">
              <template>
                <div class="item">
                  [[item.name]]
                </div>
              </template>
            </dom-repeat>
          </sortable-list>
          
          <dom-repeat items="{{response}}" as="track">
            <template>
              <div on-track="handleTrack" class="trackLink">
                <div class="trackName">
                  [[track.name]]
                </div>
                <div class="trackArtist">
                  artist
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
