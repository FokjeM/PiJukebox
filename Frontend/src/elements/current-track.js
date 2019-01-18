import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '@polymer/iron-ajax/iron-ajax.js';

import './../shared-styles.js';

class CurrentTrack extends PolymerElement {
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

       .trackInfo {
         display: flex;
         flex-direction: column;
         align-items: center;
       }

       .artistContainer {
         display:flex;
         flex-direction: row;
       }

       .artist {
         display: flex;
         flex-direction: row;
       }

       .artist:not(:last-of-type)::after {
         content: ", ";
         position: relative;
         display: block;
         right: 0;
         width: 10px;
       }
     </style>

     <iron-ajax
       auto
       method="POST"
       url="http://localhost:8080/api/v1/player/current"
       handle-as="json"
       params="{{header}}"
       last-response="{{currentTrack}}">
     </iron-ajax>

     <div class="container">
       <div class="trackInfo">
         <p>[[currentTrack.name]]</p>
         <template is="dom-if" if="{{!currentTrack.artists}}">
           <!-- No Artist -->
         </template>

         <div class="artistContainer">
           <template is="dom-if" if="{{currentTrack.artists}}">
             <template is="dom-repeat" items="{{currentTrack.artists}}" as="artist" rendered-item-count="{{artistCount}}">
               <div class="artist">
                 {{artist.name}}
                </div>
              </template>
            </template>
          </div>
          
       </div>
     </div>
   `;
 }
 static get properties() {
   return {
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

 hasArtist(){
   return this.currentTrack.artists.length > 0;
 }
}

customElements.define('current-track', CurrentTrack);