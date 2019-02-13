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

      <iron-meta key="apiPath" value="{{apiRootPath}}"></iron-meta>

      <!-- Get Play/Pause state, Volume level and Repeat state  -->
      <iron-ajax
        auto
        id="getStatus"
        url="[[apiRootPath]]/player/status"
        content-type="application/json"  
        params="{{header}}"   
        handle-as="json"
        on-response="verifyStatus">
      </iron-ajax>

      <!-- Shuffle the queue-->
      <iron-ajax
        id="shuffle"
        url="[[apiRootPath]]/player/shuffle"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus"
        on-error="handleSetError">
      </iron-ajax>

      <!-- Play current track -->
      <iron-ajax
        id="play"
        url="[[apiRootPath]]/player/playCurrent"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus"
        on-error="handleSetError">
      </iron-ajax>

      <!-- Pause current track -->
      <iron-ajax
        id="pause"
        url="[[apiRootPath]]/player/pause"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus"
        on-error="handleSetError">
      </iron-ajax>

      <!-- Toggle repeat state -->
      <iron-ajax
        id="repeat"
        url="[[apiRootPath]]/player/repeat"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus"
        on-error="handleSetError">
      </iron-ajax>

      <!-- Play next track in queue -->
      <iron-ajax
        id="nextTrack"
        url="[[apiRootPath]]/player/next"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus"
        on-error="handleSetError">
      </iron-ajax>

      <!-- Play previous track in queue -->
      <iron-ajax
        id="previousTrack"
        url="[[apiRootPath]]/player/prev"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus"
        on-error="handleSetError">
      </iron-ajax>

      <!-- Set volume level  -->
      <iron-ajax
        id="changeVolume"
        url="[[apiRootPath]]/player/volume/{{volumeLevel}}"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="getPlayerStatus"
        on-error="handleSetError">
      </iron-ajax>

      <!-- Get currently playing track and bind to {{currentTrack}}-->
      <iron-ajax
       id="getCurrentTrack"
       auto
       url="[[apiRootPath]]/player/current"
       handle-as="json"
       params="{{header}}"
       last-response="{{currentTrack}}">
     </iron-ajax>

      <div class="container">  
        <div class="controlsContainer">
         
          <div>
            <current-track
              current-track="{{currentTrack}}">
            </current-track>
          </div>

          <div class="controls">
            <paper-icon-button on-tap="shuffle" icon="av:shuffle" id="shuffleBtn"></paper-icon-button>

            <paper-icon-button on-tap="previousTrack" icon="av:skip-previous"></paper-icon-button>

            <paper-icon-button on-tap="playPause" icon="[[playPauseIcon]]"></paper-icon-button>
            
            <paper-icon-button on-tap="nextTrack" icon="av:skip-next"></paper-icon-button>

            <paper-icon-button on-tap="repeat" icon="[[repeatIcon]]" id="repeatBtn"></paper-icon-button>
          </div>

          <div class="controls">
            <iron-icon icon="[[volumeIcon]]"></iron-icon>
            <paper-slider id="volumeSlider" max="100" step="1" value="{{volumeLevel}}" on-change="changeVolumeVal"></paper-slider>
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
    this.$.getCurrentTrack.generateRequest();
  }

  // Update play/pause state, repeat state and volumeLevel (+ icons)
  verifyStatus(e, r) {
    const playerStatus = JSON.parse(r.response);
    this.updateStates(playerStatus);
    this.updateControls();
  }

  handleSetError(e,r){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Status', text: 'Could not send request.'}, bubbles: true, composed: true }));
  }

  /**
   * Set states
   */
  updateStates(playerStatus) {
    this.playPauseState = playerStatus.isPlaying;
    this.repeatState = playerStatus.repeatState;
    this.volumeLevel = playerStatus.volumeLevel;
  }

  /**
   * Update the controls
   */
  updateControls () {
    this.changePlayPauseIcon();
    this.changeRepeatIcon();
    this.changeVolumeIcon();
  }

  playPause(e) {
    const state = this.playPauseState;
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

  shuffle(e){
    this.$.shuffle.generateRequest();
    this.dispatchEvent(new CustomEvent('refresh-queue-event', { bubbles: true, composed: true }));
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
    const state = this.playPauseState;
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
    if(this.repeatState) {
      repeatButton.style.color = "var(--app-primary-color)";
      this.repeatIcon = "av:repeat";
    }
    else {
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
    const volume = this.volumeLevel;
    this.$.volumeSlider.value = volume;
    if (volume >= 1 && volume <= 50) {
      // low / medium
      this.volumeIcon = "av:volume-down";
    } else if (volume >= 51 && volume <= 100) {
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
}

customElements.define('track-control', TrackControl);