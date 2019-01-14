import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '../shared-styles.js';

import '@polymer/polymer/lib/elements/dom-repeat.js';
import '@polymer/iron-ajax/iron-ajax.js';

import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-icons/av-icons.js';
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
          padding-left: 25px;
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
      <!-- sync -->
      <iron-ajax
        auto
        id="getAllPlaylists"
        url="http://localhost:8080/api/v1/laptops"
        content-type="application/json"
        handle-as="json"
        last-response="{{playlists}}">
      </iron-ajax>

      <iron-ajax
        id="addToPlaylist"
        method="POST"
        url="http://localhost:8000/playlists/addTrack"
        content-type="application/json"
        handle-as="json"
        on-response="addedTrack">
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
            artist
          </div>
          <div class="trackTime">
            time
          </div>
        </div>
      </div>

      <paper-dialog id="dialog">
        <h3>Add <span>[[track.name]]</span> to playlist</h2>
        <dom-repeat items="{{playlists}}" as="playlist">
          <template>
            <label class="playlist" data-playlist-id$="[[playlist.id]]" data-track-id$="[[track.id]]"
                on-tap="addTrack">[[playlist.name]]</label> <br>
          </template>
        </dom-repeat>

      </paper-dialog>
      
    `;
  }

  static get properties() {
    return {
      track: {
        type: Object
      }
    };
  }

  openBy(element) {
    let alignedD = this.shadowRoot.getElementById("dialog");
    alignedD.positionTarget = element;
    alignedD.open();
  }

  addTrack(e) {
    let playlistId = e.target.dataset.playlistId;
    let trackId = e.target.dataset.trackId;
    this.$.addToPlaylist.setAttribute('body', '{"trackId":' + trackId + ', "playlistId":'+ playlistId +'}');
    this.$.addToPlaylist.generateRequest();
  }

  addedTrack(e, response) {
    if(response==200) {
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