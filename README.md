## Project: Bee-Hive ##
A visual simulation of bees in the style of John Conways <a href="https://bitstorm.org/gameoflife/">Game of Life</a>. 
The project is written in Java with the support from the framework <a href="https://libgdx.badlogicgames.com/">LibGdx</a>.

### Elements and Concept ###
There is three different elements: bees (Yellow ![](/Game/core/assets/beeNoPollen.png) or Orange if it has pollen), 
queens (Yellow with Q), hives (Black) and flowers (Red).

The concept is to simulate a bee hive with bees and neaby flowers. 
The bees will follow preset rules in the style of the Game of Life. 
Their purpose is to multiply in number and expand to a new hive.

### Rules ####

Queen rule: If two bees is near a hive, and there is no queen on the way or present, 
they will start creating a new queen. 
OOO: This does not require any resources.
<br/><br/>
![GIF Queen Rule](/GIFS/README/queenRule.gif)

Harvest rule: If the hive contains a queen, then the bee will choose a random direction and 
start searching for a flower. When found the bee will harvest it, and return to the hive.
<br/><br/>
![GIF Harvest Rule](/GIFS/README/harvestRule.gif)

New worker: If the bee is near the hive a got pollen, then it will be used to create a new bee.
<br/><br/>
![GIF Harvest Rule](/GIFS/README/newWorkerRule.gif)

Expansion rule 1: If hive contains a queen and more than ## bees, they will start creating a 
new queen bee. 

Expansion rule 2: If the hive constain two queens and more than ## bees, one queen and half of 
the worker bees will leave the hive in a random direction and find a spot to create a new hive. 
OOO: After a preset distance from the other hive. Not ontop of flowers.


### INSERT OTHER GIFS ###

BLACK = Hive, Q = Queen, YELLOW = Bee without pollen, ORANGE = Bee with pollen, FLOWER = FLOWER.
