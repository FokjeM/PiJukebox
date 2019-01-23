# PiJukebox
This is supposed to emulate a Jukebox on a Raspberry Pi
 - Play any song availlable on the system
 - Add songs to a queue
 - Play the entire queue; preferrably automatically
 - Play an existing playlist from front-end
 - Add a playlist made from songs on the system on front-end

# Okay ladies, listen up!
It'll run on the current Raspbian Lite build (No GUI by default, no useless software, lightweight and powerful) (Debian 9 based)

# What we NEED on the system
  - [x] Git, so we can download and build the latest source
   - This is included in Linux by default, possibly because Linus Torvalds is also the creator of Git
  - [x] Polymer.js so we can actually serve the frontend
    - [x] This is built on NodeJS
    - [x] This requires NPM
  - [x] JDK 8 (the JDK includes the JRE); preferrably the Oracle JDK even though it isn't open source
  - [x] Maven in order to build
  - [x] MariaDB / MySQL / MongoDB / Any DB system as per the group agreements

# How this works
  - git pull on the Pi
  - mvn build on the Pi
  - polymer serve on the Pi
  - Try looking for a way to have Maven do this for us

## This is the README.md for MartinWithController, this covers the components:
  - Media from this branch
  - The Player classes to play music and perform logging
  - Fatal Exception so we can check when to kill the Player due to an error
  - NonFatal Exception so we know when we can keep going
  - Integrating the player from origin/Martin into the rest of the code
