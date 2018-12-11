import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './../shared-styles.js';

class CurrentSong extends PolymerElement {
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
        
        .song-info {
          display: flex;
          flex-direction: column;
          align-items: center;
        }
      </style>
      <!-- shadow DOM goes here -->
      
      <div class="container">
        
        <div class="song-info">
          <p>[[songName]]</p>
          <p>[[songArtist]]</p>
        </div>
          
      </div>
    `;
  }

  static get properties() {
    return {
      songId: {
        type: Number
      },
      songName: {
        type: String
      },
      songArtist: {
        type: String
      }
    };
  }

}

customElements.define('current-song', CurrentSong);
