import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './../shared-styles.js';

class CurrentTrack extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }
        
        .container {
          display: flex;
          flex-direction: column;
        }
        
        .track-info {
          display: flex;
          flex-direction: column;
          align-items: center;
        }
      </style>
      
      <div class="container">
        
        <div class="track-info">
          <p>[[trackName]]</p>
          <p>[[trackArtist]]</p>
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

customElements.define('current-track', CurrentTrack);
