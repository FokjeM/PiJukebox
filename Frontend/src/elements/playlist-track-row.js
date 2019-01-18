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
          flex-direction: column;
          margin-bottom: 10px;
          background-color: rgba(0,121,107,0.05);
          border-radius: 5px;
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

        paper-dialog {
          padding-bottom: 10px;
        }

        h3 span {
          color: var(--app-primary-color);
        }
        
      </style>

      <iron-ajax
        id="addToPlaylist"
        method="PATCH"
        url="http://localhost:8080/api/v1/details/playlists/{{playlistId}}/tracks/{{trackId}}"
        content-type="application/json"
        handle-as="json"
        on-response="addedTrack">
      </iron-ajax>

      <iron-ajax
        auto
        id="getPlaylists"
        url="http://localhost:8080/api/v1/playlists"  
        handle-as="json"
        content-type="application/json"
        last-response="{{playlists}}">
      </iron-ajax>

      <div class="trackLink">
        <div class="trackAddToPlaylist">
          <paper-icon-button on-tap="openBy" icon="av:playlist-add"></paper-icon-button>
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
          <!-- <div class="trackTime"> -->
            <!-- time -->
          <!-- </div> -->
        </div>
      </div>

      <paper-dialog id="dialog">
        <h3>Add <span>[[track.name]]</span> to playlist</h2>
        <dom-repeat items="{{playlists}}" as="playlist">
          <template>
            <label class="playlist" data-playlist-id$="[[playlist.id]]" data-track-id$="[[track.id]]"
                on-tap="addTrack">[[playlist.title]]</label> <br>
          </template>
        </dom-repeat>
      </paper-dialog>
      
    `;
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
      }
    };
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
      this.throwEvent('open-dialog-event', {title: 'Playlist', text: 'Song is successfully added to the playlist'});
    }
    else {
      this.throwEvent('open-dialog-event', {title: 'Playlist', text: 'Something went wrong, please try again'});
    }
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