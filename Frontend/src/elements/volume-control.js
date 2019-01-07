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
        
        .controlsContainer {
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
      
      <iron-ajax
        id="changeVolume"
        method="POST"
        url="http://localhost:8080/api/v1/laptops/">
      </iron-ajax>

      <div class="container">  
        <div class="controlsContainer">
          
          <!--<div class="controls">-->
            <!--<paper-icon-button icon="av:volume-mute"></paper-icon-button>-->
            <!--<paper-icon-button icon="av:volume-down"></paper-icon-button>-->
            <!--<paper-icon-button icon="av:volume-up"></paper-icon-button>-->
          <!--</div>-->
          
          <div class="controls">
            <iron-icon icon="[[volumeIcon]]"></iron-icon>
            <paper-slider id="volumeSlider" max="10" step="1" value="2" on-change="changeVolume"></paper-slider>
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

  /**
   * This method calls the changeVolumeIcon and changeVolumeLevel methods 
   */
  changeVolume(e) {
    this.changeVolumeIcon();
    this.changeVolumeLevel(e);
  }
  
  /**
   * This method changes the volume icon according to the volume
   */
  changeVolumeIcon() {
    let volumeLevel = this.$.volumeSlider.value;
    
    if (volumeLevel >= 1 && volumeLevel <= 5) {
      // low / medium
      this.volumeIcon = "av:volume-down";
    } else if (volumeLevel >= 6 && volumeLevel <= 10) {
      // High
      this.volumeIcon = "av:volume-up";
    } else {
      // Mute
      this.volumeIcon = "av:volume-off";
    }
  }

  /**
   * This method sends the request to the backend to change the volume level
   */
  changeVolumeLevel(e) {
    let volumeLevel = this.$.volumeSlider.value;
    
    this.$.changeVolume.setAttribute('body', '{"volume":' + volumeLevel + '}');
    this.$.changeVolume.generateRequest();
  }

}

customElements.define('volume-control', VolumeControl);