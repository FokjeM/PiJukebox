import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './../shared-styles.js';

import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-icons/av-icons.js';
import '@polymer/paper-slider/paper-slider.js';

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

        .trackInfo {
          display: flex;
          flex-direction: column;
          align-items: center;
        }
        
        #volumeIcon {
          padding-top: 5px;
        }
      </style>

      <iron-ajax
        auto
        id="getStatus"
        url="http://localhost:8080/api/v1/player/status"
        content-type="application/json"  
        handle-as="json"
        last-response="{{playerStatus}}"
        on-response="verifyStatus">
      </iron-ajax>

      <iron-ajax
        id="shuffle"
        method="POST"
        url="http://localhost:8080/api/v1/player/shuffle"
        content-type="application/json"
        handle-as="json"
        on-response="getStatus">
      </iron-ajax>

      <iron-ajax
        id="playPause"
        method="POST"
        url="http://localhost:8080/api/v1/player/playpause"
        content-type="application/json"
        handle-as="json"
        on-response="getStatus">
      </iron-ajax>

      <iron-ajax
        id="repeat"
        method="POST"
        url="http://localhost:8080/api/v1/player/repeat"
        content-type="application/json"
        handle-as="json"
        on-response="getStatus">
      </iron-ajax>

      <iron-ajax
        id="nextTrack"
        method="POST"
        url="http://localhost:8080/api/v1/player/next"
        content-type="application/json"
        handle-as="json"
        on-response="getStatus">
      </iron-ajax>

      <iron-ajax
        id="previousTrack"
        method="POST"
        url="http://localhost:8080/api/v1/player/previous"
        content-type="application/json"
        handle-as="json"
        on-response="getStatus">
      </iron-ajax>

      <iron-ajax
        id="changeVolume"
        method="POST"
        url="http://localhost:8080/api/v1/player/volume"
        content-type="application/json"
        handle-as="json"
        on-response="getStatus">
      </iron-ajax>
      
      <div class="container">  
        
        <div class="trackInfo">
          <p>[[playerStatus.track.name]]</p>
          <p>[[playerStatus.track.artist]]</p>
        </div>

        <div class="controlsContainer">   
          <div class="controls">
            <paper-icon-button on-tap="shuffle" icon="av:shuffle" id="shuffleBtn"></paper-icon-button>

            <paper-icon-button on-tap="previousTrack" icon="av:skip-previous"></paper-icon-button>

            <paper-icon-button on-tap="playPause" icon="[[playPauseIcon]]"></paper-icon-button>
            
            <paper-icon-button on-tap="nextTrack" icon="av:skip-next"></paper-icon-button>

            <paper-icon-button on-tap="repeat" icon="[[repeatIcon]]" id="repeatBtn"></paper-icon-button>
          </div>

          <div class="controls">
            <iron-icon icon="[[volumeIcon]]"></iron-icon>
            <paper-slider id="volumeSlider" max="10" step="1" value="[[volumeLevel]]" on-change="changeVolume"></paper-slider>
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
      playPauseState: {
        type: Boolean,
        value: 0
      },
      repeatIcon: {
        type: String,
        value: "av:repeat"
      },
      repeatState: {
        type: Number,
        value: 0
      },
      shuffleIsActive: {
        type: Boolean,
        value: false
      },
      volumeIcon: {
        type: String,
        value: "av:volume-down"
      },
      volumeLevel: {
        type: Number,
        value: 2
      }
    };
  }


  getStatus(e) {
    this.$.getStatus.generateRequest();
  }

  verifyStatus(e, response) {
    if (response == 200) {
      this.updateStates();
      this.updateControls();
    } else {
      this.throwEvent('open-dialog-event', {title: 'Player', text: 'Something went wrong, please try again'});
    }
  }

  /**
   * Set states
   */
  updateStates() {
    this.shuffleIsActive = playerStatus.player.shuffleState;
    this.playPauseState = playerStatus.player.playPauseState;
    this.repeatState = playerStatus.player.repeatState;
    this.volumeLevel = playerStatus.player.volumeLevel;
  }

  /**
   * Update the controls
   */
  updateControls () {
    this.changeShuffleIcon();
    this.changePlayPauseIcon();
    this.changeRepeatIcon();
    this.changeVolumeIcon();
  }

  playPause(e) {
    this.$.playPause.generateRequest();
  }
  
  shuffle(e) {
    this.$.shuffle.generateRequest();
  }
  
  repeat(e) {
    this.$.repeat.generateRequest();
  }
  
  nextTrack(e) {
    this.$.nextTrack.generateRequest();
  }
  
  previousTrack(e) {
    this.$.previousTrack.generateRequest();
  }
  
  changeShuffleIcon() {
    let shuffleButton = this.$.shuffleBtn;

    if(!this.shuffleIsActive) {
      shuffleButton.style.color = "var(--app-primary-color-light)";
      this.shuffleIsActive = true;
    } else {
      shuffleButton.style.color = "var(--paper-icon-button-ink-color, var(--primary-text-color))";
      this.shuffleIsActive = false;
    }
  }

  /**
   * Change the play/pause icon to the current play / pause state
   */
  changePlayPauseIcon(state) {
    if (!state) {
      this.playPauseIcon = "av:pause";
    } else if (state) {
      this.playPauseIcon = "av:play-arrow";
    }
  }

  changeRepeatIcon() {
    let repeatButton = this.$.repeatBtn;
    // no repeat
    if(this.repeatState === 0) {
      repeatButton.style.color = "var(--app-primary-color-light)";
      this.repeatIcon = "av:repeat-one";
    }
    // repeat one track
    else if(this.repeatState === 1) {
      repeatButton.style.color = "var(--app-primary-color-light)";
      this.repeatIcon = "av:repeat";
    }
    // repeat queue
    else if(this.repeatState === 2) {
      repeatButton.style.color = "var(--paper-icon-button-ink-color, var(--primary-text-color))";
      this.repeatIcon = "av:repeat";
    }
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
    let volume = this.volumeLevel;
    this.$.volumeSlider.value = volume;
    if (volume >= 1 && volume <= 5) {
      // low / medium
      this.volumeIcon = "av:volume-down";
    } else if (volume >= 6 && volume <= 10) {
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
    let volume = this.$.volumeSlider.value;
    this.$.changeVolume.setAttribute('body', '{"volume":' + volume + '}');
    this.$.changeVolume.generateRequest();
  }

  throwEvent(name, detail){
    this.dispatchEvent(new CustomEvent(name, 
      { 
          detail: detail, 
          bubbles: true,
          composed: true, 
      }
    ));
  }

}

customElements.define('track-control', TrackControl);