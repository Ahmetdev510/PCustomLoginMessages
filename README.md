PCustomLoginMessages
===================

A plugin that allows you to customize the login messages of your players.

Commands
--------

* `/pclm loginmessage give <player> <message>` - Gives a login message to a player.
* `/pclm loginmessage take <player> <message>` - Takes a login message from a player.
* `/pclm reset <player>` - Resets the login messages of a player.
* `/pclm reload` - Reloads the configuration file.
* `/pclm` - Shows the help page.

Permissions
-----------

* `pclm.open` - Allows the player to use the `/loginmessage` command.
* `pclm.loginmessage.give` - Allows the player to use the `/pclm loginmessage give` command.
* `pclm.loginmessage.take` - Allows the player to use the `/pclm loginmessage take` command.
* `pclm.reset` - Allows the player to use the `/pclm reset` command.
* `pclm.reload` - Allows the player to use the `/pclm reload` command.

Configuration
-------------

```yaml
PCustomLoginMessages:
  prefix: '&8[&6PCustomLoginMessages&8]&7'
  default_message: '&7Welcome to the server, &6%player%&7!'
  Messages:
    noPermission: '{prefix} &cYou do not have permission to use this command.'
    reload: '{prefix} &aReloaded the plugin.'
    PlayerNotFound: '{prefix} &cPlayer not found.'
    Gived: '{prefix} &a{message} has been given a login message to {player}'
    NotGived: '{prefix} &c{message} has not been given a login message to {player}'
    Taked: '{prefix} &a{message} has been taken a login message from {player}'
    NotTaked: '{prefix} &c{message} has not been taken a login message from {player}'
    Active: '{prefix} &a{message} has been activated.'
    DisabledActive: '{prefix} &c{message} has been deactivated.'
    Reset: '{prefix} &a{player} has been reset.'
    Help:
      - '&8&m----------------------'
      - '&6PCustomLoginMessages &7Help'
      - '&8&m----------------------'
      - '&6/pclm loginmessage give <player> <message> &7- &eGives a login message to a player.'
      - '&6/pclm loginmessage take <player> <message> &7- &eTakes a login message from a player.'
      - '&6/pclm reset <player> &7- &eResets the login messages of a player.'
      - '&6/pclm reload &7- &eReloads the plugin.'
      - '&8&m----------------------'
  Permissions:
    reload: pclm.reload
    give: pclm.loginmessage.give
    take: pclm.loginmessage.take
    reset: pclm.reset
    open_command: pclm.open
  mysql:
    enabled: false
    host: 'localhost'
    port: 3306
    database: 'database'
    username: 'root'
    password: ''
    table: 'messages'
  GUI:
    open_command:
      - loginmessage
    menu_title: '&c&lLogin Messages (Page {currentPage}/{maxPage})'
    PrevPage: '&a&l← Previous Page'
    PrevPageLore: '&7Click to go to the previous page {page}'
    PrevPageMaterial: 'ARROW'
    CurrentPage: '&7&lPage: {page} of {maxPage}'
    CurrentPageLore: '&7&lYou are currently on page {page}'
    CurrentPageMaterial: 'NAME_TAG'
    NextPage: '&a&lNext Page →'
    NextPageLore: '&7Click to go to the next page {nextPage}'
    NextPageMaterial: 'ARROW'
    items:
      rocket:
        message: "&3ServerName ➤ &b%player% &elogged into the game with rocket"
        page: 0
        slot: 10
        active:
          material: REDSTONE_BLOCK
          display_name: '&aRocket Entrance'
          lore:
            - ""
            - " &8* &7Preview:"
            - " {prefix} %player% &elogged into the game with rocket"
            - ""
            - " &7Status: &aActive"
            - ""
            - " &c&nClick to close."
        passive:
          material: DIAMOND_BLOCK
          display_name: '&aRocket Entrance'
          lore:
            - ""
            - " &8* &7Preview:"
            - " {prefix} %player% &elogged into the game with rocket"
            - ""
            - " &7Status: &cClosed"
            - ""
            - " &a&nClick to select."

        no_access:
          material: COAL_BLOCK
          display_name: '&aRocket Entrance'
          lore:
            - ""
            - " &8* &7Preview:"
            - " {prefix} %player% &elogged into the game with rocket."
            - ""
            - " &7Status: &4Locked"
```

About
-----

[SpiGUI](https://github.com/SamJakob/SpiGUI) The GUI library used in this plugin.


License
-------

This plugin is licensed under the [MIT License](https://opensource.org/licenses/MIT).
