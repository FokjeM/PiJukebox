import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './shared-styles.js';
import '@polymer/polymer/lib/elements/dom-repeat.js';
import '@polymer/iron-ajax/iron-ajax.js';

import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';

import './elements/queue-item.js';

class TrackQueue extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;

          padding: 10px;
        }
        
      </style>
      
      <iron-ajax
        id="getCurrentQueue"
        auto
        url="http://localhost:8000/search/track/k"
        handle-as="json"
        last-response="{{response}}">
      </iron-ajax>

      <div class="card">  
        <div class="container">
          <h1>Current queue</h1>
          <dom-repeat items="{{response}}" as="track">
            <template>
              <queue-item
                  track-id="{{track.id}}"
                  track-name="{{track.title}}"
                  track-artist="{{track.artist}}">
              </queue-item>
            </template>
          </dom-repeat>
        </div>
      </div>

    `;
  }

  ready(){
    super.ready();
    window.addEventListener('refresh-queue-event', function(e) {
      this.$.getCurrentQueue.generateRequest();
    }.bind(this));
  }

}

customElements.define('track-queue', TrackQueue);