import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import './../shared-styles.js';

import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-icons/av-icons.js';
import '@polymer/paper-slider/paper-slider.js';

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
        params="{{header}}"   
        handle-as="json"
        on-response="verifyStatus">
      </iron-ajax>

      <iron-ajax
        id="shuffle"
        method="GET"
        url="http://localhost:8080/api/v1/player/shuffle"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus">
      </iron-ajax>

      <iron-ajax
        id="play"
        method="GET"
        url="http://localhost:8080/api/v1/player/play"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus">
      </iron-ajax>

      <iron-ajax
        id="pause"
        method="GET"
        url="http://localhost:8080/api/v1/player/pause"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus">
      </iron-ajax>

      <iron-ajax
        id="repeat"
        method="GET"
        url="http://localhost:8080/api/v1/player/repeat"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus">
      </iron-ajax>

      <iron-ajax
        id="nextTrack"
        method="GET"
        url="http://localhost:8080/api/v1/player/next"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus">
      </iron-ajax>

      <iron-ajax
        id="previousTrack"
        method="GET"
        url="http://localhost:8080/api/v1/player/prev"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus">
      </iron-ajax>

      <iron-ajax
        id="changeVolume"
        method="GET"
        url="http://localhost:8080/api/v1/player/volume/{{volumeLevel}}"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus">
      </iron-ajax>

      <div class="container">  
        <div class="controlsContainer">
         
          <div>
            <current-track
              track-name="trackName"
              track-artist="artistName">
            </current-track>
          </div>

          <div class="controls">
            <!-- <paper-icon-button on-tap="shuffle" icon="av:shuffle" id="shuffleBtn"></paper-icon-button> -->

            <paper-icon-button on-tap="previousTrack" icon="av:skip-previous"></paper-icon-button>

            <paper-icon-button on-tap="playPause" icon="[[playPauseIcon]]"></paper-icon-button>
            
            <paper-icon-button on-tap="nextTrack" icon="av:skip-next"></paper-icon-button>

            <!-- <paper-icon-button on-tap="repeat" icon="[[repeatIcon]]" id="repeatBtn"></paper-icon-button> -->
          </div>

          <div class="controls">
            <iron-icon icon="[[volumeIcon]]"></iron-icon>
            <paper-slider id="volumeSlider" max="10" step="1" value="{{volumeLevel}}" on-change="changeVolumeVal"></paper-slider>
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
        // value: false
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
        // value: 2
      },
      token: {
        type: String,
        value: localStorage.getItem("token")
      },
      header: {
        type: Object,
        reflectToAttribute: true,
        computed: '_computeTokenHeaders(token)'
      }
    };
  }
  _computeTokenHeaders(token)
  {
      return {'Authorization': token};
  }

  getPlayerStatus(e) {
    this.$.getStatus.generateRequest();
  }

  verifyStatus(e, response) {
    let playerStatus = JSON.parse(response.response);
    console.log(playerStatus);
    if (response.status == 200) {
      this.updateStates(playerStatus);
      this.updateControls();
    } else {
      this.throwEvent('open-dialog-event', {title: 'Player', text: 'Something went wrong, please try again'});
    }
  }

  /**
   * Set states
   */
  updateStates(playerStatus) {
    this.playPauseState = playerStatus.isPlaying;
    // this.repeatState = playerStatus.repeatState;
    this.volumeLevel = playerStatus.volumeLevel;
  }

  /**
   * Update the controls
   */
  updateControls () {
    this.changePlayPauseIcon();
    // this.changeRepeatIcon();
    this.changeVolumeIcon();
  }

  playPause(e) {
    let state = this.playPauseState;
    if (state) {
      // player is playing
      this.pause(e);
    } else if (!state) {
      // player is not playing
      this.play(e);
    }
  }

  play(e) {
    this.$.play.generateRequest();
  }

  pause(e){
    this.$.pause.generateRequest();
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
  changePlayPauseIcon() {
    let state = this.playPauseState;
    if (state) {
      // player is playing
      this.playPauseIcon = "av:pause";
    } else if (!state) {
      // player is not playing
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
  changeVolumeVal(e) {
    this.changeVolumeIcon();
    this.changeVolumeLevel();
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
  changeVolumeLevel() {
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