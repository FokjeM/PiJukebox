/**
 * @license
 * Copyright (c) 2016 The Polymer Project Authors. All rights reserved.
 * This code may only be used under the BSD style license found at http://polymer.github.io/LICENSE.txt
 * The complete set of authors may be found at http://polymer.github.io/AUTHORS.txt
 * The complete set of contributors may be found at http://polymer.github.io/CONTRIBUTORS.txt
 * Code distributed by Google as part of the polymer project is also
 * subject to an additional IP rights grant found at http://polymer.github.io/PATENTS.txt
 */

import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import { setPassiveTouchGestures, setRootPath } from '@polymer/polymer/lib/utils/settings.js';
import '@polymer/app-layout/app-drawer/app-drawer.js';
import '@polymer/app-layout/app-drawer-layout/app-drawer-layout.js';
import '@polymer/app-layout/app-header/app-header.js';
import '@polymer/app-layout/app-header-layout/app-header-layout.js';
import '@polymer/app-layout/app-scroll-effects/app-scroll-effects.js';
import '@polymer/app-layout/app-toolbar/app-toolbar.js';
import '@polymer/app-route/app-location.js';
import '@polymer/app-route/app-route.js';
import '@polymer/iron-pages/iron-pages.js';
import '@polymer/iron-selector/iron-selector.js';
import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/paper-dialog/paper-dialog.js';
import './my-icons.js';
import './elements/track-control.js';
import './elements/dialog-element.js';



// Gesture events like tap and track generated from touch will not be
// preventable, allowing for better scrolling performance.
setPassiveTouchGestures(true);

// Set Polymer's root path to the same value we passed to our service worker
// in `index.html`.
setRootPath(MyAppGlobals.rootPath);

class MyApp extends PolymerElement {
  static get template() {
    return html`
      <style>
        :host {
          --app-primary-color: #00796B;
          --app-primary-color-light: #b2dfdb;
          --app-secondary-color: #757575;
          --paper-slider-knob-color: var(--app-primary-color);
          --paper-slider-active-color: var(--app-primary-color);
          color:var(--app-primary-color);
          display: block;
        }

        app-drawer-layout:not([narrow]) [drawer-toggle] {
          display: none;
        }

        app-header {
          color: #fff;
          background-color: var(--app-primary-color);
        }

        app-header paper-icon-button {
          --paper-icon-button-ink-color: white;
        }

        .drawer-list {
          margin: 0 20px;
        }

        .drawer-list a {
          display: block;
          padding: 0 16px;
          text-decoration: none;
          color: var(--app-secondary-color);
          line-height: 40px;
        }

        .drawer-list a.iron-selected {
          color: black;
          font-weight: bold;
        }
        
      </style>

      <app-location route="{{route}}" url-space-regex="^[[rootPath]]">
      </app-location>

      <app-route route="{{route}}" pattern="[[rootPath]]:page" data="{{routeData}}" tail="{{subroute}}">
      </app-route>

      <app-drawer-layout fullbleed="" narrow="{{narrow}}">
        <!-- Drawer content -->
        <app-drawer id="drawer" slot="drawer" swipe-open="[[narrow]]">
          <app-toolbar>Menu</app-toolbar>
          <iron-selector selected="[[page]]" attr-for-selected="name" class="drawer-list" role="navigation">
            <a name="tracks" href="[[rootPath]]tracks">Tracks</a>
            <a name="playlists" href="[[rootPath]]playlists">Playlists</a>
            <a name="playlist" href="[[rootPath]]playlist">Single Playlist</a>
            <a name="queue" href="[[rootPath]]queue">Queue</a>
            <a name="search" href="[[rootPath]]search">Search</a>
          </iron-selector>
        </app-drawer>

        <!-- Main content -->
        <app-header-layout has-scrolling-region="">

          <app-header slot="header" condenses="" reveals="" effects="waterfall">
            <app-toolbar>
              <paper-icon-button icon="my-icons:menu" drawer-toggle=""></paper-icon-button>
              <div main-title="">PiJukeBox</div>
            </app-toolbar>
          </app-header>

          <iron-pages selected="[[page]]" attr-for-selected="name" role="main">
            <all-tracks name="tracks"></all-tracks>
            <all-playlists name="playlists"></all-playlists>
            <single-playlist name="playlist"></single-playlist>
            <track-queue name="queue"></track-queue>
            <single-artist name="artist"></single-artist>
            <single-album name="album"></single-album>
            <search-tracks name="search"></search-tracks>
            <my-login name="login"></my-login>
            <my-view404 name="view404"></my-view404>
          </iron-pages>

          <dialog-element id="mainDialog">
          </dialog-element>

          <template is="dom-if" if="[[!isLogin(page)]]">
            <track-control></track-control>
          </template>

        </app-header-layout>
      </app-drawer-layout>
    `;
  }

  isLogin(page){
    return page == 'login';
  }

  ready(){
    super.ready();
    window.addEventListener('open-dialog-event', function(e) {
      this.openDialog(e);
    }.bind(this));
  }

  openDialog(e){
    var dialog = this.shadowRoot.getElementById('mainDialog');
    dialog.dialogTitle = e.detail.title;
    dialog.dialogText = e.detail.text;
    dialog.open();
  }

  static get properties() {
    return {
      page: {
        type: String,
        reflectToAttribute: true,
        observer: '_pageChanged'
      },
      routeData: Object,
      subroute: Object
    };
  }

  static get observers() {
    return [
      '_routePageChanged(routeData.page)'
    ];
  }

  _routePageChanged(page) {
     // Show the corresponding page according to the route.
     //
     // If no page was found in the route data, page will be an empty string.
     // Show 'tracks' in that case. And if the page doesn't exist, show 'view404'.
    var token = localStorage.getItem('token');
    if(token == null){
      this.set('route.path', '/login');
      this.page = 'login';
    }
    else if (!page) {
      this.page = 'tracks';
    } 
    else if (['tracks', 'playlists', 'playlist', 'queue', 'search', 'artist', 'album', 'login'].indexOf(page) !== -1) {
      this.page = page;
    } 
    else {
      this.page = 'view404';
    }

    // Close a non-persistent drawer when the page & route are changed.
    if (!this.$.drawer.persistent) {
      this.$.drawer.close();
    }
  }

  _pageChanged(page) {
    // Import the page component on demand.
    //
    // Note: `polymer build` doesn't like string concatenation in the import
    // statement, so break it up.
    switch (page) {
      case 'tracks':
        import('./all-tracks.js');
        break;
      case 'playlists':
        import('./all-playlists.js');
        break;
      case 'playlist':
        import('./single-playlist.js');
        break;
      case 'queue':
        import('./track-queue.js');
      case 'artist':
        import('./single-artist.js');
        break;  
      case 'album':
        import('./single-album.js');
        break;    
      case 'search':
        import('./search-tracks.js');
        break;  
      case 'login':
        import('./my-login.js');
        break;  
      case 'view404':
        import('./my-view404.js');
        break;
    }
  }
}

window.customElements.define('my-app', MyApp);
