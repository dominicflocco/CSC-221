
import java.util.*;

/** Looks in the Boggle board and figures out the board cells needed
 *  to construct a given word, using a recursive procedure.
 * 
 * 
 * @author Henry Howell
 * @author Dominic Flocco 
 * 
 * Time Spent: 3 hours
 * 
 */
public class WordOnBoardFinder implements IWordOnBoardFinder {
    
    /** Calls on a recursive helper for each cell on a given board and 
     * determines the board cells needed to construct a given word. 
     * 
     * Tests each board cell as a starting point and determines whether a path for a
     * given word can be found. If the work does not exists in the board, this method 
     * returns an empty list.
     * 
     * @param board - a board of type BoggleBoard that is made up of BoardCells.
     * @param word - the word guessed that is being checked whether it can be made 
     * given the letters on board. 
     * 
     * @return cellList - a List of BoardCells needed to construct a given word. 
     * If the word does not exists, cellList is empty.
     */
    public List<BoardCell> cellsForWord(BoggleBoard board, String word) {

        List<BoardCell> cellList = new ArrayList<BoardCell>();
        
        // for each row and column on the board, run the helper to see if 
        // the letter matches the word 
        for(int r = 0; r < board.size(); r++) {
            for(int c = 0; c < board.size(); c++){
                // if the helper returns true, return cellList becasue the word 
                // has been found
                if (findPath(board, r, c, 0, word, cellList)) { 
                    return cellList;
                }
            }
        }
        return cellList; // returns an emtpy list if the word is not on the board
        
    }
    /** Helper function that finds the correct path of a given word.
     * 
     * Once we have found the correct letter based on the index of the word being checked
     * the board cell of that letter is added to cellList and the method returns true. 
     * If the letter does not match the index, the method backtracks and checks the next
     * neighbor.
     * 
     * @param board - a board of type BoggleBoard that is made up of BoardCells.
     * @param row - current row index under consideration.
     * @param col - current column index under consideration.
     * @param index - keeps track of which letter in the word has been checked.
     * @param word - the word whose path is being checked on the board.
     * @param cellList - list of boardcells that keeps track of which letters have 
     * been guessed.
     * @return - a boolean where true signifies that word has been found.
     */
    private boolean findPath(BoggleBoard board, int row, int col, int index, 
        String word, List<BoardCell> cellList) {
            
        // base case that says a word cannot be one letter (rules of Boggle)
        if (word.length() == 1){
            return false;
        }
        
        String curFace = board.getFace(row,col); 
        String curLetter = String.valueOf(word.charAt(index));

        // changes index and curLetter for the "Qu" exception
        if (curFace.equals("qu")){ 
            if (curLetter.equals("q")){
                curLetter = word.substring(index,index+2);
                index++;
            }
        }
        
        // tests whether the current (row, col) entry matches word at index
        if (!curLetter.equals(curFace)){ 
            return false;
        }
        
        BoardCell curCell = new BoardCell(row,col);
        List<BoardCell> neighbors = neighborList(board, curCell);
        
        // checks for duplicates of the current cell in cellList
        // if the cell has not already been used, it is added to cellList
        if (!cellList.contains(curCell)){
            cellList.add(new BoardCell(row,col));
        }
        else {
            return false;
        }
        
        // checks if the given word has been found by testing if the method has 
        // successfully made it to the end of the given word
        if (index == word.length() - 1) { 
                return true;
                }
                
        // recursively calls the function for each neighbor of the current cell
        // in order to check which path to go down 
        for (int i = 0; i < neighbors.size(); i++){
            int curRow = neighbors.get(i).getRow(); 
            int curCol = neighbors.get(i).getCol();
            
            // recursive call
            if (findPath(board, curRow, curCol, index + 1, word, cellList)) {
                return true;
                }
            }
        //backtracking 
        cellList.remove(index); 
        return false;
        }
    /** Helper function that returns all the neighbors of a given cell. 
     * 
     * A neighbor is any cell that is next to or diagonal from the current cell. 
     * This method ensures that the neighbor is valid and on the board by implementing
     * isNeighbor() from the BoardCell class.
     * 
     * @param board - a board of type BoggleBoard that is made up of BoardCells.
     * @param curCell - the current cell whose neighbors will be looked at.
     * @return - a list of the board cells that are valid neighbors of curCell.
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



