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
          <paper-icon-button icon="av:queue"></paper-icon-button>
          <div>[[trackName]]</div>
          <div style="margin:0 10px;"> - </div>
          <div>[[trackArtist]]</div>
        </div>
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

customElements.define('result-row-track', ResultRowTrack);
