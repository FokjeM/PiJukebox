# PiJukebox
This is supposed to emulate a Jukebox on a Raspberry Pi
 - Play any song availlable on the system
 - Add songs to a queue
 - Play an existing playlist
 - Add a playlist made from songs on the system

# Okay ladies, listen up!
It'll run on the current Raspbian Lite build (No GUI by default, no useless software, lightweight and powerful) (Debian 9 based)

# What we NEED on the system
  - [x] Git, so we can download and build the latest source
   - This is included in Linux by default, possibly because Linus Torvalds is also the creator of Git
  - [ ] Polymer.js so we can actually serve the frontend
    - [ ] This is built on NodeJS
    - [ ] This requires NPM
  - [ ] JDK 8 (the JDK includes the JRE); preferrably the Oracle JDK even though it isn't open source
  - [ ] Maven in order to build
  - [ ] MariaDB / MySQL / MongoDB / Any DB system as per the group agreements

# How this works
  - Write a component
  - Test it
  - Group testing
  - Still works? Add to master
  - git pull on the Pi
  - mvn build on the Pi
  - polymer serve on the Pi
    - Try looking for a way to have Maven do this for us

## This is the README.md for MASTER, this covers ALL COMPONENTS
