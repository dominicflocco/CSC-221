import java.util.*;

/** Uses a recursive algorithm to find all valid words in a given boggle board
 * based on a lexicon. 
 * 
 * @author Dominic Flocco 
 * @author Henry Howell 
 * 
 * Time Spent: 6 hours
 */
public class AutoPlayer extends AbstractAutoPlayer {

    /** Looks in the Boggle board and finds all words that can be formed
     * (given the lexicon), using a recursive procedure.
     * 
     * Uses a recursive helper function to find all valid words at a given starting
     * point in the board. Tests all board cells as a starting point with a for loop.
     * Returns a list of all words on the board after each cell has been tested.
     * 
     * @param board - a given board of type BoggleBoard that is made up of BoardCells.
     * @param lex - a lexicon of words that is used to check whether a word 
     * guessed is valid.
     * @return - wordList - a list of strings with all the valid words in a given board.
     */
    
    public List<String> findAllValidWords(BoggleBoard board, ILexicon lex) {
    
        // initializes wordList so it can be appended in helper function when a
        // valid word is found
        List<String> wordList = new ArrayList<String>();
        List<BoardCell> cellList = new ArrayList<BoardCell>();
        
        // tests all letters by calling helper 
        for(int r = 0; r < board.size(); r++) {
            for(int c = 0; c < board.size(); c++){
                wordListFinder(board, lex, r, c, "", wordList, cellList);
            }
        }
        return wordList;

    }
    
    /** Recursive method that uses the given board to generate words by checking whether
     * each word appears in the lexicon. 
     * 
     * Builds the word with each recursive call and then usesthe wordStatus method from 
     * the ILexicon class to test whether the word is a word, prefix or not a word. If 
     * the current string is a word, it is added to the list, if it is not a word, then
     * the next neighbor is tested and if it is a prefix the word continues to grow.
     * 
     * @param board - a given board of type BoggleBoard that is made up of BoardCells.
     * @param lex - a lexicon of words that is used to check whether a word 
     * guessed is valid.
     * @param row - current row under consideration.
     * @param col - current column under consideration. 
     * @param word - a string that is being built and ultimately checked if it is 
     * in the lexicon
     * @param wordList - a list of strings that is added to each time a word is valid
     * @param cellList - a list of boardcells that keeps track of which cells 
     * on the baord have been checked
     */
    private void wordListFinder(BoggleBoard board, ILexicon lex, int row, int col, 
        String word, List<String> wordList, List<BoardCell> cellList) {
        
        BoardCell cell = new BoardCell(row, col); 
        String face = board.getFace(row,col);
        List<BoardCell> neighbors = neighborList(board, cell); 

        // checks whether the current cell has already been visited
        if (cellList.contains(cell)){
            return;
        }
        
        // if the cell hasn't been visited, add the letter to word
        word += face;
        
        LexStatus status = lex.wordStatus(word);

        // if the current state of word is not a valid word, then 
        // it stops and checks the next cell
        if (status == LexStatus.NOT_WORD){
            return;
        }
        
        // only after it is confirmed that the cell is not already 
        // in the cellList and that word is either a word or prefix,
        // is the cell added to cellList
        cellList.add(cell);

        if (status == LexStatus.WORD){
            wordList.add(word);
        }
        // recursive call for each neighbor of the current row and column
        for (int i = 0; i < neighbors.size(); i++){
            int newRow = neighbors.get(i).getRow(); 
            int newCol = neighbors.get(i).getCol();
            wordListFinder(board, lex, newRow, newCol, word, wordList, cellList);
        }
        // backtracking
        cellList.remove(cellList.size()-1);
    }

     /** Helper function that returns all the neighbors of a given cell. 
     * 
     * A neighbor is any cell that is next to or diagonal from the current cell. 
     * This method ensures that the neighbor is valid and on the board by implementing
     * isNeighbor() from the BoardCell class.
     * 
     * @param board - a board of type BoggleBoard that is made up of BoardCells
     * and can be either 3x3 or 4x4 
     * @param curCell - the current cell whose neighbors will be looked at
     * @return - a list of the board cells that are valid neighbors of curCell
     */
    private List<BoardCell> neighborList(BoggleBoard board, BoardCell curCell){
        List<BoardCell> neighbors = new ArrayList<BoardCell>();
        
        // checks each cell on the board and adds all the neighbors to the list
        for(int r = 0; r < board.size(); r++) {
            for(int c = 0; c < board.size(); c++){
                BoardCell testCell = new BoardCell(r,c);
                if (curCell.isNeighbor(testCell)){
                    neighbors.add(testCell);
                }
            }
        }
        return neighbors;
    }   
}
