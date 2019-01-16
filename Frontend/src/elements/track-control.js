import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './../shared-styles.js';

import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-icons/av-icons.js';

import './current-track.js';

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
         
          <div>
            <current-track
              track-name="trackName"
              track-artist="artistName">
            </current-track>
          </div>

          <div class="controls">
            <paper-icon-button on-tap="shuffle" icon="av:shuffle" id="shuffleBtn"></paper-icon-button>

            <paper-icon-button on-tap="previousTrack" icon="av:skip-previous"></paper-icon-button>

            <paper-icon-button on-tap="playPause" icon="[[playPauseIcon]]"></paper-icon-button>
            
            <paper-icon-button on-tap="nextTrack" icon="av:skip-next"></paper-icon-button>

            <paper-icon-button on-tap="repeat" icon="[[repeatIcon]]" id="repeatBtn"></paper-icon-button>
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
      },
      repeatIcon: {
        type: String,
        value: "av:repeat"
      },
      repeatState: {
        type: String,
        value: 0
      },
      shuffleIsActive: {
        type: Boolean,
        value: false
      }
    };
  }

  shuffle() {
    let shuffleButton = this.$.shuffleBtn;

    if(!this.shuffleIsActive) {
      shuffleButton.style.color = "blue";
      this.shuffleIsActive = true;
    } else {
      shuffleButton.style.color = "var(--paper-icon-button-ink-color, var(--primary-text-color))";
      this.shuffleIsActive = false;
    }
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

  repeat() {
    let repeatButton = this.$.repeatBtn;

    if(!this.repeatIsActive) {
      repeatButton.style.color = "blue";
      this.repeatIcon = "av:repeat-one"; // repeat-one icon
      this.repeatIsActive = true;

    } else {
      repeatButton.style.color = "var(--paper-icon-button-ink-color, var(--primary-text-color))";
      this.repeatIcon = "av:repeat";
      this.repeatIsActive = false;
    }
  }

}

customElements.define('track-control', TrackControl);