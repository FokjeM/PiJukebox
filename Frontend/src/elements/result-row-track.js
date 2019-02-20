import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class ResultRowTrack extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
  
        .track-info {
          display: flex;
        }
        .artist {
          display: flex;
        }

        .artist:not(:last-of-type)::after {
          content: ", ";
          position: relative;
          display: block;
          right: 0;
          width: 10px;
        }

        .addTrackToQueue{
          color: var(--app-primary-color);
        }
        
      </style>

      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>

      <iron-ajax
        id="addToQueue"
        method="post"
        url="[[apiRootPath]]/player/add/[[trackId]]"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="handleQueueResponse"
        on-error="handleError">
      </iron-ajax>
      
      <div>
        <div class="track-info">
          <paper-icon-button class="addTrackToQueue" icon="av:queue" on-tap="addToQueue"></paper-icon-button>
          <div style="display:flex; padding:8px;">
            <div>[[trackName]]</div>
          
            <template is="dom-if" if="[[!excludeArtist]]">
              <div style="margin:0 10px;"> - </div>
              <template is="dom-repeat" items="{{trackArtist}}" as="artist" rendered-item-count="{{artistCount}}">
                <div class="artist">
                  {{artist.name}}
                </div>
              </template>
            </template>

            <template is="dom-if" if="{{!artistCount}}">
              No Artists.
            </template>
            
          </div>  
        </div>
      </div>

    `;
  }

  addToQueue(){
    this.shadowRoot.getElementById('addToQueue').generateRequest();
  }

  handleQueueResponse(){
    this.dispatchEvent(new CustomEvent('refresh-queue-event', { bubbles: true, composed: true }));
    this.dispatchEvent(new CustomEvent('refresh-track-control-event', { bubbles: true, composed: true }));
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: this.trackName + ' has been added to the queue.'}, bubbles: true, composed: true }));
  }

  handleError(){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: 'Something went wrong.'}, bubbles: true,composed: true }));
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
        type: Object
      },
      artistCount: {
        type: Number
      },
      excludeArtist:{
        type: Boolean,
        value: false
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

customElements.define('result-row-track', ResultRowTrack);
