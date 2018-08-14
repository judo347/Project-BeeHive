A grafical simulation of bees in the style of the Game of Life.

### Elemtents and Concept ###

There will be 3 different elements: bees (Yellow), hives (Blue) and flowers (Red).

The concept is to simulate a bee hive with bees and neaby flowers. The bees will follow preset rules in the style of the Game of Life. Their purpose is to multiply in number and expand to a new hive.

### Rules ####

Queen rule: If two bees is near a hive, and there is no queen on the way or present, 
they will start creating a new queen. 
OOO: This does not require any resources.
![GIF Queen Rule](/GIFS/README/queenRule.gif)

Harvest rule: If the hive contains a queen, then the bee will choose a random direction and 
start searching for a flower. When found the bee will harvest it, and return to the hive.

New worker: If the bee is near the hive a got pollen, then it will be used to create a new bee.

Expansion rule 1: If hive contains a queen and more than ## bees, they will start creating a 
new queen bee. 

Expansion rule 2: If the hive constain two queens and more than ## bees, one queen and half of 
the worker bees will leave the hive in a random direction and find a spot to create a new hive. 
OOO: After a preset distance from the other hive. Not ontop of flowers.
