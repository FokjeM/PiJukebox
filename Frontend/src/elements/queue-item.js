import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class QueueItem extends PolymerElement {
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

      <div class="queueItem">
        <div class="controls">
          <paper-icon-button on-click="oneUp" icon="arrow-upward"></paper-icon-button>
          <paper-icon-button on-click="oneDown" icon="arrow-downward"></paper-icon-button>
        </div>
        <div class="trackLink">
        <div class="trackName">
          [[trackName]]
        </div>
        <div class="trackArtist">
          [[trackArtist]]
        </div>
       </div>
      </div>

      <iron-ajax
        id="queueUp"
        method="post"
        url="http://localhost:8000/queue/moveup"
        body='[{"trackId": [[trackId]]}]'
        content-type="application/json"
        handle-as="json"
        on-response="handleQueueResponseUp">
      </iron-ajax>

      <iron-ajax
        id="queueDown"
        method="post"
        url="http://localhost:8000/queue/movedown"
        body='[{"trackId": [[trackId]]}]'
        content-type="application/json"
        handle-as="json"
        on-response="handleQueueResponseDown">
      </iron-ajax>

    `;
  }

  oneUp(e) {
    this.$.queueUp.generateRequest();
  }

  oneDown(e) {
    this.$.queueDown.generateRequest();
  }

  handleQueueResponseUp(e,r){
    if(r.status == 200){
      this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: this.trackName + ' has been moved up.'}, bubbles: true,composed: true, }));
      this.dispatchEvent(new CustomEvent('refresh-queue-event', { bubbles: true,composed: true }));
    }
    else{
      this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: 'Something went wrong.'}, bubbles: true,composed: true, }));
    }
  }

  handleQueueResponseDown(e,r){
    if(r.status == 200){
      this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: this.trackName + ' has been moved down.'}, bubbles: true,composed: true, }));
      this.dispatchEvent(new CustomEvent('refresh-queue-event', { bubbles: true,composed: true }));
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
      }
    };
  }

}

customElements.define('queue-item', QueueItem);
