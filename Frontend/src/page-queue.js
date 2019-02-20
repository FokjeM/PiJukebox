import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './shared-styles.js';
import '@polymer/polymer/lib/elements/dom-repeat.js';
import '@polymer/iron-ajax/iron-ajax.js';

import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/paper-button/paper-button.js';
import './elements/queue-item.js';

class PageQueue extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
          padding: 10px;
        }
        #clearQueue {
          font-size:14px;
          color: var(--app-primary-color);
        }
        #clearQueue:hover {
          cursor: pointer;
        }
      </style>

      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>
      
      <iron-ajax
        id="getCurrentQueue"
        auto
        url="[[apiRootPath]]/player/queue"
        params="{{header}}"
        handle-as="json"
        last-response="{{queueTracks}}"
        on-response="getResponse">
      </iron-ajax>

      <iron-ajax
        method="POST"
        id="clearQueue"
        url="[[apiRootPath]]/player/queue/clear"
        params="{{header}}"
        handle-as="json"
        on-response="queueCleared"
        on-error="queueClearedError">
      </iron-ajax>

      <div class="card">  
        <div class="container">
          <h1>Current queue</h1>
          <dom-repeat id="domRepeat" items="{{queueTracks}}" as="track" index-as="innerIndex" rendered-item-count="{{queueTrackCount}}">
          <template>
              <queue-item
                  track-id="{{track.id}}"
                  track-name="{{track.name}}"
                  track-artists="{{track.artists}}"
                  track-index={{innerIndex}}>
              </queue-item>
            </template>
          </dom-repeat>
        </div>
        <template is="dom-if" if="{{queueTrackCount}}">
          <paper-button id="clearQueue" on-click="clearQueue">Clear queue</paper-button>
        </template> 
        <template is="dom-if" if="{{!queueTrackCount}}">
          No tracks in queue.
        </template> 
      </div>

    `;
  }

  ready(){
    super.ready();
    window.addEventListener('refresh-queue-event', function() {
      this.$.getCurrentQueue.generateRequest();
    }.bind(this));
  }
 
  clearQueue(){
    this.$.clearQueue.generateRequest();
  }

  queueChanged() {
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: 'The queue changed successfully'}, bubbles: true, composed: true }));
  }

  queueChangedError(){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: 'Something went wrong, please try again'}, bubbles: true, composed: true }));
  }

  queueCleared(){
    this.$.getCurrentQueue.generateRequest(); // Refresh Queue
    this.dispatchEvent(new CustomEvent('refresh-track-control-event', { bubbles: true, composed: true }));
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: 'The queue has been cleaned successfully'}, bubbles: true, composed: true }));
  }

  queueClearedError(){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: 'Something went wrong, please try again'}, bubbles: true, composed: true }));
  }

  getResponse(e, r) {
    this.response = r.response;
  }

  static get properties() {
    return {
      queueTracks: {
        type: Object
      },
      queueTrackCount: {
        type: Number
      },
      token: {
        type: String,
        value: localStorage.getItem("token")
      },
      header: {
        type: Object,
        reflectToAttribute: true,
        computed: '_computeTokenHeaders(token)'
      },
      response: {
        type: Array,
        value: []
      }
    };
  }
  _computeTokenHeaders(token)
  {
      return {'Authorization': token};
  }
  
}

customElements.define('page-queue', PageQueue);