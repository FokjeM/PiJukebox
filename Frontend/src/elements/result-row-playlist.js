import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class ResultRowPlaylist extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles"></style>
      <div class="resultRow">
        <a href="[[rootPath]]playlist/[[playlistId]]">
          <div>
              <div>[[playlistName]]</div>
          </div>
        </a>
      </div>
    `;
  }

  static get properties() {
    return {
      playlistId: {
        type: Number
      },
      playlistName: {
        type: String
      }
    };
  }

}

customElements.define('result-row-playlist', ResultRowPlaylist);
