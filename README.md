# PiJukebox
This is supposed to emulate a Jukebox on a Raspberry Pi, with some mediaplayer enhancements
  - Play any song availlable on the system
  - Add songs to a queue
  - Play an existing playlist
  - Create a playlist made from songs on the system
  - Pause and Resume functionality
  - Next and Previous functionality
    - No skip, seek or restart song functionality though

# Okay ladies, listen up!
It'll run on the Raspbian Lite (No GUI by default, no useless software, lightweight and powerful) (Debian 9 based)
  - Updates and patches will be applied
  - Oracle JDK 8 will be used
  - The most recent Maven in the Raspbian APT repositories will be used
  - Tomcat through Cargo
    - We use a plugin from the Maven repositories
    - The JRE Build target is 1.8 (Java 8)
    - The most recent Tomcat build only promises to work with JRE/JDK 8
    - Tomcat can run the backwards compatible parts of any newer JRE/JDK
      - This means Java 11 could be used, but anything introduced in Java >= 9 won't work!
      - Raspbian only has the JRE/JDK availlable up to Oracle JDK 8 though

# What we NEED on the system
  - [x] Git, so we can download and build the latest source
   - This is included in Linux by default, possibly because Linus Torvalds is also the creator of Git
  - [x] Polymer.js so we can actually serve the frontend
    - This is built on NodeJS
    - This requires NPM
  - [x] JDK 8 (the JDK includes the JRE); the Oracle JDK even though it isn't open source
  - [x] Maven in order to build
  - [x] MariaDB (Tested and working) / MySQL (Preferred)
