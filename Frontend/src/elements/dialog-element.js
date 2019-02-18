import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

class DialogElement extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        #dialog{
          max-width:600px;
          padding-bottom:60px;
        }
        .closeDialog{
          position: absolute;
          right: 0;
          color: var(--app-primary-color);
        }
        .closeDialog:hover{
          cursor: pointer;
        }
      </style>
      
      <div>
        <paper-dialog id="dialog">
          <h2>[[dialogTitle]]</h2>
          <p>[[dialogText]]</p>
          <paper-button class="closeDialog" on-click="closeDialog">Close</paper-button>
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

  open() {
    this.$.dialog.open();
  }

  closeDialog(){
    this.$.dialog.close();
  }
}

customElements.define('dialog-element', DialogElement);
