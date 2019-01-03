import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './../shared-styles.js';

import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-icons/av-icons.js';

/**
 * All the icons that are part of iron-icons
 * https://npm-demos.appspot.com/@polymer/iron-icons@3.0.1/demo/index.html
 */

class TrackControl extends PolymerElement {
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
      </style>
      
      <div class="container">  
        <div class="controlsContainer">
         
          <div class="controls">
            <paper-icon-button on-tap="shuffle" icon="av:shuffle"></paper-icon-button>

            <paper-icon-button on-tap="previousTrack" icon="av:skip-previous"></paper-icon-button>

            <paper-icon-button on-tap="playPause" icon="[[playPauseIcon]]"></paper-icon-button>
            
            <paper-icon-button on-tap="nextTrack" icon="av:skip-next"></paper-icon-button>

            <paper-icon-button on-tap="repeat" icon="av:repeat"></paper-icon-button>
          </div>
          
        </div>
      </div>
    `;
  }

  static get properties() {
    return {
      playPauseIcon: {
        type: String,
        value: "av:play-arrow"
      }
    };
  }

  playPause() {
    
    // try {
    //   //todo: decide how this will be handled by backend
    //   this.$.playPauseTrack.setAttribute('body', '{"playPause":' + volumeLevel + '}');
    //   this.$.playPauseTrack.generateRequest();
    // } catch(e) {
    //   alert('Error');
    // }

    this.changePlayPauseIcon();
  }

  // TODO: maybe change the method in a way that the icon will be changed according 
  //       to a param instead of checking what the current icon is and change on that. 
  /**
   * Change the play/pause icon to the current play / pause state
   */
  changePlayPauseIcon() {
    if (this.playPauseIcon === "av:play-arrow") {
      this.playPauseIcon = "av:pause";
    } else {
      this.playPauseIcon = "av:play-arrow"
    }
  }

}

customElements.define('track-control', TrackControl);