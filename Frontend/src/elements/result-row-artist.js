import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class ResultRowArtist extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles"></style>
      <div class="resultRow">
        <a href="[[rootPath]]artist/[[artistId]]">
          <div>
              <div>[[artistName]]</div>
          </div>
        </a>
      </div>
    `;
  }

  static get properties() {
    return {
      artistId: {
        type: Number
      },
      artistName: {
        type: String
      }
    };
  }

}

customElements.define('result-row-artist', ResultRowArtist);
