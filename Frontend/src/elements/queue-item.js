import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class QueueItem extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
  
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

      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>

      <iron-ajax
        id="queueUp"
        url="[[apiRootPath]]/player/move/track/up/[[trackIndex]]"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="handleQueueResponseUp"
        on-error="handleError">
      </iron-ajax>

      <iron-ajax
        id="queueDown"
        url="[[apiRootPath]]/player/move/track/down/[[trackIndex]]"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="handleQueueResponseDown"
        on-error="handleError">
      </iron-ajax>

      <div class="queueItem">
        <!-- <div class="controls">
          <template is="dom-if" if="[[isFirst()]]">
            <paper-icon-button on-tap="oneUp" icon="arrow-upward" disabled></paper-icon-button>
          </template> 

          <template is="dom-if" if="[[!isFirst()]]">
            <paper-icon-button on-tap="oneUp" icon="arrow-upward"></paper-icon-button>
          </template>  

          <paper-icon-button class="downButton" on-tap="oneDown" icon="arrow-downward"></paper-icon-button>
        </div> -->
        <div class="trackLink">
          <div class="trackName">
            [[trackName]]
          </div>
          <div class="trackArtist">
            [[trackArtist]]
          </div>
        </div>
      </div>
    `;
  }

  ready(){
    super.ready();
    this.trackArtists.forEach(element => {
        this.trackArtist = this.trackArtist + " " + element.name;
    });
  }

  isFirst(){
    return this.trackIndex == 0;
  }

  oneUp() {
    this.$.queueUp.generateRequest();
  }

  oneDown() {
    this.$.queueDown.generateRequest();
  }

  handleQueueResponseUp(){
      this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: this.trackName + ' has been moved up.'}, bubbles: true,composed: true, }));
      this.dispatchEvent(new CustomEvent('refresh-queue-event', { bubbles: true,composed: true }));
  }

  handleQueueResponseDown(){
      this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: this.trackName + ' has been moved down.'}, bubbles: true,composed: true, }));
      this.dispatchEvent(new CustomEvent('refresh-queue-event', { bubbles: true,composed: true }));
  }

  handleError(){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: 'Something went wrong.'}, bubbles: true,composed: true, }));
  }

  static get properties() {
    return {
      trackIndex: {
        type: Number
      },
      trackId: {
        type: Number
      },
      trackName: {
        type: String
      },
      trackArtists: {
        type: Array
      },
      trackArtist: {
        type: String,
        value: "Artists: "
      },
      trackIndex: {
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
      }
    };
  }
  _computeTokenHeaders(token)
  {
      return {'Authorization': token};
  }

}

customElements.define('queue-item', QueueItem);
