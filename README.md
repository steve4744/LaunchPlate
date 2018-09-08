# LaunchPlate

The launch plate is created using a pressure plate placed on top of a normal block. When a player steps on the plate, he/she will be launched upwards to whatever height is configured. The type of block and type of pressure plate are both configurable in the config.yml. The default block is obsidian with a gold pressure plate placed on top.

The height to which the player is launched is controlled by the "force" variable in the configuration file, set with the "/lp setforce" command. The actual height achieved can be controlled to a very good degree of accuracy by specifying the force as a decimal. The default force is 0.8 which will launch the player about 16 blocks up.  Unlike a lot of similar plugins, there is no limit to the height a player can be launched to. So, for example, a force of 4.0 will launch the player about 60 blocks up, while a force of 8.0 will launch the player about 128 blocks up.

No fall damage is inflicted on the players, so can be used in any gamemode.

The plugin can be used as a fun addition to a lobby/hub or as an extension to a parkour course.

Starting with version 2.0 of LaunchPlate, the plugin will only run on Minecraft versions 1.13 and later.
Version 1.7 of LaunchPlate is the final release to support all Minecraft versions from 1.8 through to 1.12.2.

## Download

LaunchPlate can be [downloaded from Spigot](https://www.spigotmc.org/resources/launch-plate.42251/ "LaunchPlate by steve4744")

## Features

    set the base block to any valid MC 1.13.1 material <br>
    set the pressure plate to any pressure plate type<br>
    tab completion enabled
    add a Minecraft sound effect on launch <br>
    - use tab completion to select any Minecraft sound from list<br>
    add a Minecraft particle effect on launch<br>
    - use tab completion to select any Minecraft particle effect from list<br>
    adjust the height of the bounce by setting the force variable<br>
    - no limit to height achievable<br>
    optionally give the player a forward trajectory instead of a vertical bounce<br>
    - adjust the force of the forward trajectory and the height and distance travelled<br>
    permission launchplate.admin needed to change any of the config<br>
    permission launchplate.use can be used to enable/disable launchplates in specific worlds or to groups of players<br>
    bstats enabled to collect anonymous usage stats<br>


## Commands

The plugin LuckPerms uses the same command alias "lp", so although tab completion makes this redundant to some extent, there is an additional alias "lpl" which can be used for LaunchPlate if you also have the LuckPerms plugin on your server.

    /lp setblock - set the base block of the launchplate (Material)
    /lp setplate - set the type of pressure plate (Material)
    /lp setsound - set a sound effect on launch
    /lp settrail - set a particle effect on launch
    /lp setforce - set height acheivable (default is 0.8)
    /lp reload - reloads the config.yml file


All of the above require launchplate.admin permission

    /lp help - displays this list of commands
    /lp - information about the plugin

<br>
<br>
Updated 08 Sept 2018 by steve4744

