import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class ResultRowAlbum extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }
      </style>
      
      <a href="[[rootPath]]album/[[albumId]]">
        <div>
            <div>[[albumName]]</div>
        </div>
      </a>
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
