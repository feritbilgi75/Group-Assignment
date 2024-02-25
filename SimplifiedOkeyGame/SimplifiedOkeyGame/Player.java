public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: checks this player's hand to determine if this player is winning
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     */
    public boolean checkWinning() {
        for(int i = 0 ; i < playerTiles.length - 1; i++){
            if(playerTiles[i + 1].getValue() - playerTiles[i].getValue() >  1 ){
                return false;
            }
        }
        return true;
    }

    /*
     * TODO: used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     */
    public int findLongestChain() {
        int longestChain = 1;// chain length can be at least one.
        int curLen = 1;
        
        for(int i = 0 ; i < playerTiles.length - 1; i++){
            if(playerTiles[i + 1].getValue() - playerTiles[i].getValue() == 1 ){
                curLen++;
            }
            else if(curLen > longestChain){
                longestChain = curLen;
                curLen = 1;
            }
            else{
                curLen = 1;
            }
        }
        if(curLen > longestChain){
            longestChain = curLen;
        }
        return longestChain;
    }

    /*
     * TODO: removes and returns the tile in given index position
     */
    public Tile getAndRemoveTile(int index) {
         Tile removed = playerTiles[index];

        for(int i = index; i < playerTiles.length - 1 ; i++){
            playerTiles[i] = playerTiles[i + 1];
        }
        playerTiles[playerTiles.length - 1] = null;

        return removed;
    }

    /*
     * TODO: adds the given tile to this player's hand keeping the ascending order
     * this requires you to loop over the existing tiles to find the correct position,
     * then shift the remaining tiles to the right by one
     */
    public void addTile(Tile t) {
        boolean posFound = false;
       int searchVal = t.getValue();
       int newPos = 0;
       int average = 0;
       int maxVal = playerTiles[playerTiles.length - 1].getValue();
       int maxIndex = playerTiles.length - 1;
       int minVal = playerTiles[0].getValue();
       int minIndex = 0;

       //find the index of place to be added.
       while(!posFound){
            if(searchVal > minVal && searchVal < maxVal){
                average = (minIndex + maxIndex) / 2;
                if(searchVal >= playerTiles[average].getValue()){
                    minVal = playerTiles[average].getValue();
                    minIndex = average;
                }
                else{
                    maxVal = playerTiles[average].getValue();
                    maxIndex = average;
                }
            }
            if(maxVal <= searchVal || (maxIndex - minIndex == 1)){
                newPos = maxIndex;
                posFound = true;
            }
            if(minVal >= searchVal){
                newPos = minIndex;
                posFound = true;
            }
       }
        //shift the tiles positon to right
       for(int i = playerTiles.length - 1; i > newPos ; i--){
            playerTiles[i] = playerTiles[i - 1];
       }

       playerTiles[newPos] = t;
    }

    /*
     * finds the index for a given tile in this player's hand
     */
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /*
     * displays the tiles of this player
     */
    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
