import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

import '@polymer/polymer/lib/elements/dom-repeat.js';
import '@polymer/iron-ajax/iron-ajax.js';

import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-icons/av-icons.js';
import '@polymer/iron-icons/social-icons.js';
import '@polymer/paper-dialog/paper-dialog.js';

class PlaylistTrackRow extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
        }
        
        .trackLink {
          display: flex;
          justify-content: space-between;
          margin-bottom: 10px;
          background-color: rgba(0,121,107,0.05);
          border-radius: 5px;
          padding: 10px 20px 10px 7px;
        }
        
        .trackAddToPlaylist {
          display: flex;
          align-items:center;
          flex-direction: row;
          flex-wrap: wrap;
        }

        .trackInfo {
          display: flex;
          flex-direction: row;
          justify-content: space-between;
          flex-wrap: wrap;
        }

        .trackArtist {
          display: flex;
          align-items:center;
        }

        .artist {
          display: flex;
        }

        .artist:not(:last-of-type)::after {
          content: ", ";
          position: relative;
          display: block;
          right: 0;
          width: 10px;
        }

        .artistIcon {
          color: var(--app-secondary-color);
        }

        .playlist {
          cursor: pointer;
        }

        .playlistRow {
          color: var(--app-primary-color);
        }

        #dialog{
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

        paper-dialog {
          padding-bottom: 10px;
        }

        h3 span {
          color: var(--app-primary-color);
        }

        .button{
          color: var(--app-primary-color);
        }
        
        @media(min-width: 1300px) {
          .trackArtist {
            width: 300px;
          }
        }

        @media(max-width: 1299.5px) {
          .trackArtist {
            width: 175px;
          }
        }
      </style>

      <iron-ajax
        id="addToPlaylist"
        method="PATCH"
        url="http://localhost:8080/api/v1/details/playlists/{{playlistId}}/tracks/{{trackId}}"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="addedTrack">
      </iron-ajax>

      <iron-ajax
        auto
        id="getPlaylists"
        url="http://localhost:8080/api/v1/playlists"  
        params="{{header}}"
        handle-as="json"
        content-type="application/json"
        last-response="{{playlists}}">
      </iron-ajax>

      <iron-ajax
        id="addTrackToQueue"
        method="get"
        url="http://localhost:8080/api/v1/player/add/{{trackId}}"
        content-type="application/json"
        params="{{header}}"
        handle-as="json"
        on-response="handleQueueResponse"
        on-error="handleError">
      </iron-ajax>

      <div class="trackLink">
        <div class="trackAddToPlaylist">
          <paper-icon-button class="button" icon="av:queue" on-tap="addToQueue"></paper-icon-button>
          <paper-icon-button class="button" icon="av:playlist-add" on-tap="openBy"></paper-icon-button>
          <div class="trackName">
            [[track.name]]
          </div>
        </div>
        <div class="trackInfo">
          <div class="trackArtist">
            <template is="dom-if" if="[[hasArtist()]]">
              <paper-icon-button disabled class="artistIcon" icon="social:person"></paper-icon-button>
              <template is="dom-repeat" items="{{track.artists}}" as="artist" rendered-item-count="{{artistCount}}">
                <div class="artist">
                  {{artist.name}}
                </div>
              </template>
            </template>
          </div>
        </div>
      </div>

      <paper-dialog id="dialog">
        <h3>Click a playlist to add <span>[[track.name]]</span> </h2>
        <dom-repeat items="{{playlists}}" as="playlist">
          <template>
            <div class="playlistRow">
              <label class="playlist" data-playlist-id$="[[playlist.id]]" data-track-id$="[[track.id]]"
                on-tap="addTrack">[[playlist.title]]</label>
            </div>    
          </template>
        </dom-repeat>
        <paper-button class="closeDialog" on-click="closeDialog">Close</paper-button>
      </paper-dialog>
      
    `;
  }

  closeDialog(){
    this.$.dialog.close();
  }

  static get properties() {
    return {
      track: {
        type: Object
      },
      trackId: {
        type: Number,
        value: null
      },
      playlistId: {
        type: Number,
        value: null
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

  ready(){
    super.ready();
    window.addEventListener('refresh-playlists-event', function(e) {
      this.$.getPlaylists.generateRequest();
    }.bind(this));
  }

  hasArtist(){
    return this.track.artists.length > 0;
  }

  openBy(element) {
    let alignedD = this.shadowRoot.getElementById("dialog");
    alignedD.positionTarget = element;
    alignedD.open();
  }

  addTrack(e) {
    let playlist_Id = e.target.dataset.playlistId;
    let track_Id = e.target.dataset.trackId;

    this.playlistId = playlist_Id;
    this.trackId = track_Id;
    this.$.addToPlaylist.generateRequest();
  }

  addedTrack(e, response) {
    if(response.status == 200) {
      this.dispatchEvent(new CustomEvent('refresh-playlist-event', { bubbles: true, composed: true }));
      this.throwEvent('open-dialog-event', {title: 'Playlist', text: 'Song is successfully added to the playlist'});
    }
    else {
      this.throwEvent('open-dialog-event', {title: 'Playlist', text: 'Something went wrong, please try again'});
    }
  }

  addToQueue(e){
    this.trackId = this.track.id;
    this.$.addTrackToQueue.generateRequest();
  }

  handleQueueResponse(e,r){
    this.dispatchEvent(new CustomEvent('refresh-queue-event', { bubbles: true, composed: true }));
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: this.track.name + ' has been added to the queue.'}, bubbles: true,composed: true }));
  }

  handleError(e,r){
    this.dispatchEvent(new CustomEvent('open-dialog-event', { detail: {title: 'Queue', text: 'Something went wrong.'}, bubbles: true,composed: true }));
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

customElements.define('playlist-track-row', PlaylistTrackRow);