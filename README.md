# Unit Construct

## About
This is my first java mod. It is highly experimental, and as such, the code is likely fairly messy and unwieldy (steal code at your own risk). As you can probably tell by the name, this mod is inspired by the Minecraft mod Tinker's Construct, which has an elaborate customization system for making tools. The goal of this mod is to bring a similar level of customization to Mindustry to create a deeper gameplay experience.

## V7 Compatibility
V7 compatibility is planned (if the mod is still alive at that point). The mod is currently fully functional in the latest builds of v7, although the units have some issues with outlining in the core database.

## Compiling

### Required applications
- JDK 8.

### Windows

Plain Jar: gradlew build
Dexify Plain Jar: gradlew dexify
Build Plain & Dexify Jar: gradlew buildDex

### Desktop/Mac

Plain Jar: ./gradlew build
Dexify Plain Jar: ./gradlew dexify
Build Plain & Dexify Jar: ./gradlew buildDex

## Mechanics

### Arming Units
Normally, once you finish building your units, they will start off with all their weapons ready to shoot. However, once you finish chassis units, they will start as mostly unarmed units with now weapons (aside from the default weapons, which are fairly weak). To give them weapons, construct a weapon depot. Each weapon depot can supply a single type of weapon to any chassis unit.

Click on a friendly weapon depot and click on any of the buttons at the bottom to fill the corresponding slot of a chassis with a weapon. If there is a friendly chassis unit within range of the depot, it will be equipped with the weapon associated the depot, and the required cost for the weapon will be consumed from the depot.

Attempting to put a weapon into a slot that does not exist on a unit or attempting to mount a weapon when the depot has not been given enough supplies will result in an error message, and the materials in the depot will not be consumed. However, mounting a weapon in a slot that already has a weapon mounted on it will simply replace the existing weapon in that slot.

## Credits

Mod created by GenericSomeone
Mod is built off of Sh1penfire/Endless-Rusting, which is built off of Sonnicon/mindustry-modtemplate
Entity mapping by GlennFolker
Special thanks to the above authors, as well as anyone who helped me in Discord