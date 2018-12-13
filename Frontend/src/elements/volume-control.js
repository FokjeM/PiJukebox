import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './../shared-styles.js';

import '@polymer/paper-button/paper-button.js';
import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-icons/av-icons.js';
import '@polymer/paper-slider/paper-slider.js';

/**
 * All the icons that are part of iron-icons
 * https://npm-demos.appspot.com/@polymer/iron-icons@3.0.1/demo/index.html
 */

class VolumeControl extends PolymerElement {
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
        
        .controls-container {
          display: flex;
          flex-direction: column;
        }
        
        .controls {
          display: flex;
          flex-direction: row;
          justify-content: center;
          margin-top: 10px;
        }
        
        #volumeIcon {
          /*color: red;*/
          padding-top: 5px;
        }
        
        #volumeSlider {
          color: red;
          
        }
      </style>
      
      <div class="container">  
        <div class="controls-container">
          
          <!--<div class="controls">-->
            <!--<paper-icon-button icon="av:volume-mute"></paper-icon-button>-->
            <!--<paper-icon-button icon="av:volume-down"></paper-icon-button>-->
            <!--<paper-icon-button icon="av:volume-up"></paper-icon-button>-->
          <!--</div>-->
          
          <div class="controls">
            <iron-icon icon="[[volumeIcon]]"></iron-icon>
            <paper-slider id="volumeSlider" max="10" step="1" value="2" on-change="changeVolumeIcon"></paper-slider>
          </div>
          
        </div>
      </div>
    `;
  }

  static get properties() {
    return {
      volumeIcon: {
        type: String,
        value: "av:volume-down"
      }
    };
  }

  changeVolumeIcon() {
    let volume = this.$.volumeSlider;
    let volumeLevel = volume.value;

    if (volumeLevel >= 1 && volumeLevel <= 5) {
      // low / medium
      this.volumeIcon = "av:volume-down";
    } else if (volumeLevel >= 6 && volumeLevel <= 10) {
      //high
      this.volumeIcon = "av:volume-up";
    } else {
      // mute
      this.volumeIcon = "av:volume-off";
    }
  }

}

customElements.define('volume-control', VolumeControl);