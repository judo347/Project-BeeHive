### Current known bugs ###
- GameMap.findEmptyCell: Does not check layer > 1 correctly.
- GameMap.isBeeNearFlower: return: might contain bugs.

### ToDo (Improvements) ###
- TESTS!
- GameMap: Only one rule per tick. So you cannot spawn a queen and move at the same tick.
- GameMap: Bees should "bounce" when they hit a wall
- Hive.ownedBees: is this used at all atm? Or only for expanstion. MAKE SURE BEES ARE GETTING ADDED.
- GameMap: Optimize so that the map has a list of the hives and such..
- LibGdx: Dispose method for all assets.