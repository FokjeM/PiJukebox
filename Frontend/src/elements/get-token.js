import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';

class GetToken extends PolymerElement {

  static get properties() {
    return {
      token: {
        type: Object,
        notify: true,
        reflectToAttribute: true,
        value: 'defaultGetToken'
      }
    };
  }

  ready(){
    super.ready();
    this.token = localStorage.getItem('token');
  }
}

customElements.define('get-token', GetToken);
