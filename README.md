# LaunchPlate

A launch plate is created by placing a pressure plate on top of any other block. When a player steps on the plate, he/she will be launched upwards to any configurable height. The type of block and type of pressure plate are both configurable in the config.yml and can be set using commands. The default block is obsidian with a gold pressure plate placed on top.

The height to which the player is launched is controlled by the "force" variable in the configuration file, set with the "/lpl setforce" command. The actual height achieved can be controlled to a  good degree of accuracy by specifying the force as a decimal. The default force is 0.8 which will launch the player about 4 blocks up, while a force of 1.8 will launch the player about 16 blocks up. Unlike a lot of similar plugins, there is no limit to the height a player can be launched to. So, for example, a force of 4.0 will launch the player about 60 blocks up, while a force of 8.0 will launch the player about 128 blocks up.

No fall damage is inflicted on the players, so LaunchPlate can be used in any gamemode.

LaunchPlate can be used as a fun addition to a lobby/hub or even as an extension to a parkour course.

## Versions
The latest version of LaunchPlate requires Java 16 and is for Minecraft 1.17+.

Version 3.0 of Launchplate is supported on Minecraft 1.16.5.

The versions below are available for download but are no longer supported:

Version 2.5 will run on all Minecraft versions from 1.13 to 1.15.2.<br>
Version 1.8 is for Minecraft 1.9 to 1.12.2 can be [downloaded from GitHub](https://github.com/steve4744/LaunchPlate/releases/download/v1.8/LaunchPlate.jar "LaunchPlate v1.8 by steve4744")<br>
Version 1.7 is for Minecraft 1.8 can be downloaded from Spigot.

## Download

All LaunchPlate versions can be [downloaded from Spigot](https://www.spigotmc.org/resources/launch-plate.42251/ "LaunchPlate by steve4744")

## Features

    set the base block to any valid Minecraft material
    set the pressure plate to any pressure plate type
    tab completion is enabled
    add a Minecraft sound effect on launch
    - use tab completion to select any Minecraft sound from the list
    add a Minecraft particle effect on launch
    - use tab completion to select any Minecraft particle effect from the list
    adjust the height of the bounce by setting the force variable
    - no limit to height achievable
    optionally give the player a forward trajectory instead of a vertical bounce
    - adjust the force of the forward trajectory and the height and distance travelled
    permission "launchplate.admin" is needed to change any of the configuration settings
    permission "launchplate.use" can enable/disable launchplates in specific worlds or for groups of players
    metrics - bstats is enabled to collect anonymous usage stats


## Commands

The plugin LuckPerms uses the same command alias "lp", so although tab completion makes this redundant to some extent, there is an additional alias "lpl" which can be used for LaunchPlate if you also have the LuckPerms plugin on your server.

    /lp setblock - set the base block of the launchplate (Material)
    /lp setplate - set the type of pressure plate (Material)
    /lp setsound - set a sound effect on launch
    /lp settrail - set a particle effect on launch
    /lp setforce - set force of bounce which determines the height achievable (default is 0.8)
    /lp setvertical - if set to true (default), player will launch vertically
    /lp reload - reloads the config.yml file

All of the above require the "launchplate.admin" permission

    /lp help - display this list of commands
    /lp list - display the current settings
    /lp - information about the plugin

<br>
<br>
Updated 02 July 2021 by steve4744

