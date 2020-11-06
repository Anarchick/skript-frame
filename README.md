# skript-frame
A [**Skript**](https://github.com/SkriptLang/Skript) addon for item frame and image stuff.

# NOTE
 - Since async skript effect does not allow to use temporal variable, it's recommanded to use global variable for storing images.
 - You need to download the plugin BKCommonLib (https://www.spigotmc.org/resources/bkcommonlib.39590/) for some stuff.
 - If you need more information contact me on any skript related discord
 
# EXEMPLE
```java
command /test:
    trigger:
        broadcast "%mc color from rgb(128, 177, 57)% should return 127, 178, 56"
        if {i} is true:
            set {i} to false
            set {test} to image url "https://i.imgur.com/fC0OOYE.png"
        else:
            set {i} to true
            set {test} to image file "claptrap.png"
        broadcast "%width of image {test}%px - %height of image {test}%px"
        set {_sub} to the image {test}'s cropped image at (0, 0, 600 and 600)
        save image {test} at "test.png"
        resize image {_sub} by 256 and 256
        draw image {test} to map 0 for player
```

# TODO
- Create a screen (screens will be a large image in the world using item frames)
- Event on click on a screen
- Create a new map item
- Generate a treasure map
- Get push or pull transport stream video
- add support for gif
- add layer Images (Simple image combinaison, usefull for interactive item frame)
- place a marker at a location on mapview
- convert a mapview to a bufferedimage
