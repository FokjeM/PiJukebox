import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class ResultRowTrack extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }
        .track-info {
          display: flex;
        }
      </style>

      <div>
        <div class="track-info">
          <paper-icon-button icon="av:queue" on-click="addToQueue"></paper-icon-button>
          <div style="display:flex; padding:8px;">
            <div>[[trackName]]</div>
          
            <template is="dom-if" if="[[!excludeArtist]]">
              <div style="margin:0 10px;"> - </div>
              <div>[[trackArtist]]</div>
            </template>

          </div>  
        </div>
      </div>

      <iron-ajax
        id="addToQueue"
        method="post"
        url="http://localhost:8080/api/v1/queue/add"
        body='[{"trackId": [[trackId]]}]'
        content-type="application/json"
        handle-as="json"
        on-response="handleQueueResponse">
      </iron-ajax>

    `;
  }
  
  addToQueue(e){
    this.shadowRoot.getElementById('addToQueue').generateRequest();
  }

  handleQueueResponse(e,r){
    if(r.status == 200){
      this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: this.trackName + ' has been added to the queue.'}, bubbles: true,composed: true, }));
    }
    else{
      this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: 'Something went wrong.'}, bubbles: true,composed: true, }));
    }
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
      },
      excludeArtist:{
        type: Boolean,
        value: false
      }
    };
  }

}

customElements.define('result-row-track', ResultRowTrack);