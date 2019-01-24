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

## This is the README.md for Martin, this covers the components:
  - Media from this branch
  - Diagrams made for this branch
  - The Player class to play music
  - The ErrorLogger class to track and write errors to a file
  - Fatal Exception so we can check when to kill the Player due to an error
  - NonFatal Exception so we know when we can keep going
  - Queue class so we can keep playing and follow what tracks we have left
  - Track class to store a pointer to the actual song
