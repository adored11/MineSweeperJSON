import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;




/**
 * Created by john on 09/11/2016.
 */
public class MineSweeperJSON {
    MineSweeperUtils utils = new MineSweeperUtils();
    private int [][] dimensions = { {10,10} };
    List initialGridLayout = new ArrayList();
    List populatedGridLayout = new ArrayList();
    LinkedHashMap<Integer,String> gameBoardLayoutMap = new LinkedHashMap<Integer, String>();
    String test;
    JSONObject object = new JSONObject();
    //JSONArray jsonArray = new JSONArray();

    public static void main(String [] args){
        MineSweeperJSON mineSweeper = new MineSweeperJSON();
    }
    public MineSweeperJSON(){

        int [] individualGameDimensions = new int[2];
        for (int i=0; i<dimensions.length;i++) {
            System.out.println("Field #" + (i+1));
            individualGameDimensions[0] = dimensions[i][0];
            individualGameDimensions[1] = dimensions[i][1];
            createGridLayout(individualGameDimensions);
            addRandomMines(initialGridLayout);
            gameBoardLayoutMap = updateSurrounding(initialGridLayout);
            object = formatToJSON(gameBoardLayoutMap, individualGameDimensions[0], individualGameDimensions[1] );

            //object.put(("game board "+i), "");

            //jsonArray.add(("game board "+i));
            /*jsonArray.add(gameBoardLayoutMap.values());
            object.put("game" +i, jsonArray.toJSONString());
            object.put("rows", individualGameDimensions[0]);
            object.put("columns", individualGameDimensions[1]);*/

            System.out.println(object);

            //jsonArray.clear();

            //printOutput(gameBoardLayoutMap, dimensions[i][1]);
            //test = gameBoardLayoutMap.toString();
        }
    }

    public List createGridLayout(int []individualGameDimensions){
        StringBuilder b = new StringBuilder();
            for (int j = 0; j<individualGameDimensions[1]; j++) {
                for (int i = 0; i < individualGameDimensions[0]; i++){
                    b.append("0");
                }
                initialGridLayout.add(j, utils.addToList(b.toString()));
                b.delete(0, b.length());
            }
        return initialGridLayout;
    }

    public List addRandomMines(List initialGridLayout) {
        int genRandomRowNumber = 0;
        String getRandRowContent;
        String rowMinusBrackets;
        StringBuffer mineString = new StringBuffer();
        int newRowLength;
        int getMinePosition;
        int numMines = utils.getNumMines(initialGridLayout.size(), (initialGridLayout.get(0).toString().length() -2) );

        for (int i = 0; i < numMines; i++) {
            genRandomRowNumber = utils.getRandCoord(initialGridLayout.size());
            getRandRowContent = initialGridLayout.get(genRandomRowNumber).toString();
            rowMinusBrackets = getRandRowContent.substring(1, (getRandRowContent.length() - 1));
            newRowLength = rowMinusBrackets.length();
            getMinePosition = utils.getRandCoord(newRowLength);
            // loop through by row length. getMinePosition starts at position 0 hence (getMinePosition + 1)
            // add the character at each position to buffer - mineString at minePosition append a "*"
            for (int rowPosition = 1; rowPosition <= rowMinusBrackets.length(); rowPosition++) {
                if (rowPosition < (getMinePosition + 1)) {
                    mineString.append(rowMinusBrackets.charAt(rowPosition - 1));
                } else if (rowPosition == (getMinePosition + 1)) {
                    if ( (rowMinusBrackets.substring((rowPosition-1),(rowPosition)).equals("*"))){
                        i--;
                        mineString.append("*");
                    }
                    else {
                        mineString.append("*");
                    }
                } else {
                    mineString.append(rowMinusBrackets.charAt(rowPosition - 1));
                }
            }
            mineString.insert(0, "[");
            mineString.append("]");
            //System.out.println("mineString = " + mineString);
            initialGridLayout.set(genRandomRowNumber, mineString.toString());
            mineString.delete(0, mineString.length());
        }
        return initialGridLayout;
    }

    public LinkedHashMap updateSurrounding(List initialGridLayout){

        String rowMinusBrackets;
        String rowToString;
        rowToString = initialGridLayout.get(0).toString();
        int rowStringLength;
        rowStringLength = rowToString.length() -2;
        int keyPlaceHolder = 0;

        for (int rowNumber = 0; rowNumber<initialGridLayout.size(); rowNumber++){
            rowToString = initialGridLayout.get(rowNumber).toString();
            rowMinusBrackets = rowToString.substring(1, (rowToString.length() - 1));
            rowStringLength = rowMinusBrackets.length();
            for (int rowPosition = 0; rowPosition<rowStringLength; rowPosition++){
                //System.out.println("key = " + (keyPlaceHolder) + ", " + "value = "+ rowMinusBrackets.charAt(rowNumber) );
                keyPlaceHolder++;
                gameBoardLayoutMap.put((keyPlaceHolder), String.valueOf(rowMinusBrackets.charAt(rowPosition)));
            }
        }
        String mapSquareCharacter;
        int key = 0;
        // Loop through gameBoardLayoutMap and extract key and value
        for (Map.Entry<Integer, String> square : gameBoardLayoutMap.entrySet()) {
            key = square.getKey();
            mapSquareCharacter = square.getValue();
            //System.out.println("key- "+ key + " " + "value- " + squareChar);

            // Count surrounding squares to the mine "*" - different depending on mine position on the grid
            if (mapSquareCharacter.equals("*")) {
                int []numberOfSurrounding = utils.countSurroundingSquares(gameBoardLayoutMap, key, rowStringLength);
                //System.out.println("numberOfSurrounding Square = " + Arrays.toString(numberOfSurrounding));

                // for each surrounding square, lookup the value from key and pass to returnChar() if it's NOT a mine (*)
                // This will pass back a number relating to the number of mines near each square
                for (int keyNumber=0; keyNumber<numberOfSurrounding.length; keyNumber++){
                    int keyValue = numberOfSurrounding[keyNumber];
                    String mapEntryAtKey = gameBoardLayoutMap.get(keyValue);
                    if (!(mapEntryAtKey.equals("*")) ) {
                        gameBoardLayoutMap.put(numberOfSurrounding[keyNumber], utils.returnChar(mapEntryAtKey));
                    }
                    //System.out.println("gameBoardLayoutMap = " + Arrays.asList(gameBoardLayoutMap));
                }
            }
        }
        initialGridLayout.clear();
        return gameBoardLayoutMap;
    }

    public JSONObject formatToJSON(LinkedHashMap gameBoardLayoutMap, int x, int y){
        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        int rowTracker = 0;
        for (int i=1; i<= x; i++){
            if (rowTracker < 1) {
                for (int j = 0; j < y; j++) {
                    jsonArray.add(j, gameBoardLayoutMap.get(j + 1));
                }
                //object.put("line" + i, jsonArray);
                //jsonArray.clear();
                rowTracker++;
            }    
            else if (rowTracker <= y) {
                //(rowlenth * current row number)
                for (int j = 0; j < y; j++) {
                    jsonArray.add(j, gameBoardLayoutMap.get((x * rowTracker) + (j + 1)));
                }
                object.put("line", jsonArray);
                //jsonArray.clear();
                rowTracker++;
            }
        }
        object.put("NumRows", x);
        object.put("RowLength", y);

        return object;
    }

    public void printOutput(LinkedHashMap gameBoardLayoutMap, int numRowCharacters ){

        //int numRowCharacters = gameBoardLayoutMap.size();
        StringBuilder outputLine = new StringBuilder();
        int rowPositionCount=1;
        for (int i = 1; i<=gameBoardLayoutMap.size(); i++) {
            if(rowPositionCount<=numRowCharacters){
                outputLine.append(gameBoardLayoutMap.get(i)); //add character at position i
                rowPositionCount++;
            }
            else {
                System.out.println(outputLine.toString()); //print output line
                rowPositionCount=1;
                i--;    //subtract 1 from the current loop to redo the current iteration since we've reached the start of a new row
                outputLine.delete(0, outputLine.length()); //clear stringBuilder
            }
        }
        System.out.println(outputLine.toString());  // print final output line and clear objects
        outputLine.delete(0, outputLine.length());
        System.out.println("\n");
        gameBoardLayoutMap.clear();
    }

    /* Getter's and Setter's */

    public int[][] getDimensions(){
        return dimensions;
    }
    public void setDimensions(int[][] dimensions){
        this.dimensions = dimensions;
    }

    public List getGridLayout(){
        return initialGridLayout;
    }
    public void setGridLayout(List initialGridLayout){
        this.initialGridLayout = initialGridLayout;
    }
    public List getPopulatedGridLayout(){
        return populatedGridLayout;
    }
    public void setPopulatedGridLayout(List populatedGridLayout){
        this.populatedGridLayout = populatedGridLayout;
    }
    public String getTest() {return test;}


}
