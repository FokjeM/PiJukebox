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

        #cleanQueue {
          background-color:#c74545;
          -moz-border-radius:28px;
          -webkit-border-radius:28px;
          border-radius:28px;
          border:1px solid #ab1919;
          display:inline-block;
          cursor:pointer;
          color:#ffffff;
          font-family:Arial;
          font-size:17px;
          padding:16px 31px;
          text-decoration:none;
          text-shadow:0px 1px 0px #662828;
        }
        #cleanQueue:hover {
          background-color:#bd2a2a;
        }
        #cleanQueue:active {
          position:relative;
          top:1px;
        }
      }
        
      </style>
      
      <iron-ajax
        id="getCurrentQueue"
        auto
        url="http://localhost:8080/api/v1/player/queue/"
        params="{{header}}"
        handle-as="json"
        last-response="{{response}}">
      </iron-ajax>

      <iron-ajax
        id="changeQueue"
        method="POST"
        url="http://localhost:8080/api/v1/player/changeQueue/"
        params="{{header}}"
        handle-as="json"
        on-response="queueChanged">
      </iron-ajax>

      <div class="card">  
        <div class="container">
        <button id="cleanQueue">Clean Queue</button>
          <h1>Current queue</h1>
          <dom-repeat items="{{response}}" as="track">
            <template>
              <queue-item
                  track-id="{{track.id}}"
                  track-name="{{track.name}}"
                  track-artist="{{track.artists}}">
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
 
  oneDown(e) {
    let trackToChange = e.target.dataset.trackId;
    console.log("down: " + e.target.dataset.trackId);
    this.$.changeQueue.setAttribute('body', '{"id":' + trackToChange + ', "direction":"down"}');
    this.$.changeQueue.generateRequest();
  }

  queueChanged(e, response) {
    if(response.status == 200) {
      this.throwEvent('open-dialog-event', {title: 'Queue', text: 'The queue changed successfully'});
    } else {
      this.throwEvent('open-dialog-event', {title: 'Queue', text: 'Something went wrong, please try again'});
    }
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

customElements.define('track-queue', TrackQueue);