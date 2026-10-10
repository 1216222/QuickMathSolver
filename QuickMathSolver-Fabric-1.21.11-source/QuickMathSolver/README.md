# Quick Math Solver (Fabric 1.21.11)

Client-side Fabric mod intended for Minecraft 1.21.11. It watches chat for messages containing `Quick Math:` followed by a simple two-number arithmetic expression, such as `Quick Math: 2 + 19 = ?`, and sends the answer to chat.

## Controls
- `/mathsolver` toggles the mod on/off.
- `/mathsolver on` enables it.
- `/mathsolver off` disables it.

## Supported operations
Addition, subtraction, multiplication, and division. Supports integers and decimal operands. Division by zero is ignored.

## Build
Requires JDK 21 and internet access for Gradle/Fabric dependencies:

```bash
gradle build
```

The JAR will be in `build/libs/`.

## Important
This is source code, not a prebuilt JAR. The current build environment had no Gradle installation and could not resolve external hosts, so the mod could not be compiled or tested here. Fabric/Yarn/API versions may need adjustment if the specified artifact versions are unavailable. Automated chat responses may violate some servers' rules; use only where permitted.
