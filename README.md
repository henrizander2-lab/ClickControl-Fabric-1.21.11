# ClickControl (Fabric 1.21.11) — Starter project

This is a **source project**, not a precompiled `.jar`. It is a starting point for a client-side hold-to-use mod with:
- toggle
- speed setting (delay in ticks)
- item blacklist stored in config

## Important
This project has not been compiled or tested in a Minecraft/Norris Client installation. API/mixin names can vary between Fabric mappings and Minecraft versions. It is not a ready-to-install mod yet.

Norris Client must support ordinary Fabric client mods. This mod does not interact with Norris-specific APIs.

## Build (Windows)
1. Install JDK 21 and IntelliJ IDEA.
2. Open this folder in IntelliJ as a Gradle project.
3. Let Gradle download dependencies.
4. Run `gradlew.bat build`.
5. If the build succeeds, the mod jar should be in `build/libs/`.
6. Put that jar into the Fabric/Norris `mods` folder, then test in a singleplayer world first.

## In-game
The keybind opens a basic config screen. The code uses a deliberately simple UI and config format.
