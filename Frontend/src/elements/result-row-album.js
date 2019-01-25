import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class ResultRowAlbum extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles"></style>
      <div class="resultRow">
        <a href="[[rootPath]]album/[[albumId]]">
          <div>
              <div>[[albumName]]</div>
          </div>
        </a>
      </div>
    `;
  }

  static get properties() {
    return {
      albumId: {
        type: Number
      },
      albumName: {
        type: String
      }
    };
  }

}

customElements.define('result-row-album', ResultRowAlbum);
