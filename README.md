# Sigma Fighter

A 2D tile-based action RPG written in pure Java (Swing), with no game engine.

> **Work in progress.** Core engine, movement, collision, NPCs and menus are in place; gameplay systems are still being built.

## What I added beyond the tutorial

The project started from RyiSnow's *How to Make a 2D Game in Java* YouTube series. I wrote all code by hand and extended it with:

- **8-directional movement** (the tutorial uses 4) with sub-pixel `double` positions and diagonal speed normalized by √2, so diagonal movement is not faster.
- **Per-axis collision resolution** (`collisionX` / `collisionY`): blocking one axis no longer stops the other, so entities slide along walls instead of sticking on diagonal contact.
- **Fixed a collision look-ahead bug**: integer hitboxes truncated fractional speed (2.83 → 2 px), so collision checks looked less far ahead than the entity actually moved. Speed is now rounded up to a single integer step.
- **Out-of-bounds safety**: tiles outside the map are treated as solid.
- **NPC AI**: random wandering in 8 directions with randomized decision intervals (90–190 frames) and immediate re-routing on collision. A chase behavior that steers NPCs toward the player with a dead-zone tolerance is prototyped (currently disabled).
- **Entity-to-entity collision**: NPCs collide with each other and with the player, with a short stun on contact.
- **Enum-based state machines** for game state, title menu, class selection, direction and object types instead of integer constants.

## Controls

| Key | Action |
|---|---|
| W A S D | Move (combine for diagonals) |
| Enter | Talk to an NPC / close dialogue / confirm in menus |
| Space | Next dialogue line |
| P | Pause / resume |
| T | Toggle draw-time debug display |

## Running

1. Open the project in IntelliJ IDEA.
2. Mark the `res` folder as **Resources Root**.
3. Run `main.main`.

## Credits

- Engine structure based on [RyiSnow's 2D game tutorial series](https://www.youtube.com/@RyiSnow).
- Sprites, tiles, sounds and music are from that tutorial and are not my work. They are used here for learning purposes only.
