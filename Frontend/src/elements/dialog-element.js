import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class DialogElement extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }
        #dialog{
          max-width:600px;
        }
      </style>
      
      <div>
        <paper-dialog id="dialog">
          <h2>[[dialogTitle]]</h2>
          <p>[[dialogText]]</p>
        </paper-dialog>
      </div>
    `;
  }

  static get properties() {
    return {
      dialogTitle:{
        type: String
      },
      dialogText: {
        type: String
      }
    };
  }

  open(e) {
    this.$.dialog.open();
  }
}

customElements.define('dialog-element', DialogElement);
