import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class TrackInfo extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }
        
        .container {
       
        }
        
        .track-info {
          display: flex;
      
        }
      </style>
      
      <div class="container">
        
        <div class="track-info">
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

customElements.define('track-info', TrackInfo);
