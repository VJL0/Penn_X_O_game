//IMPORT LIBRARIES HERE
import java.util.Arrays;
import java.util.Random;

public class main {
  static Random rand = new Random();
  
  public static void printBoard(String[][] agents){
    for (String[] innerarray:agents){
      for (String value:innerarray) System.out.print("["+value+"]");
      System.out.println();
    }
    System.out.println();
  }
  
  public static String[][] createBoard(int size, int totalX, int totalO) {
    //  Creates 2d Array with default value ( _ )
    String[][] agents = new String[size][size];
    if (totalX+totalO > size*size)return agents;
    for (String[] row : agents) Arrays.fill(row, "_");
    
    // Places X  and O @ random places
    for (int countX=0; countX<totalX; countX++){
      int randRow = rand.nextInt(size);
      int randColumn = rand.nextInt(size);
      if ("XO".contains(agents[randRow][randColumn])) totalX++;
      else agents[randRow][randColumn]="X";
    }
    for (int countO=0; countO<totalO; countO++){
      int randRow = rand.nextInt(size);
      int randColumn = rand.nextInt(size);
      if ("XO".contains(agents[randRow][randColumn])) totalO++;
      else agents[randRow][randColumn]="O";
    }
    
    return agents;
  }
  
  public static boolean isSatisfied(String[][] agents, int row, int col, double threshold) {
    if (agents[row][col]=="_") return false;

    int firstRow, lastRow, firstCol, lastCol;
    int countX=0;
    int countO=0;
    double s=0;
    double d=0;
    
    if (row-1<0) firstRow=row;
    else firstRow=row-1;
    if (col-1<0) firstCol=col;
    else firstCol=col-1;
    if (row+1>=agents.length) lastRow=row;
    else lastRow=row+1;
    if (col+1>=agents.length) lastCol=col;
    else lastCol=col+1;

    //System.out.println("AROUND Agent "+agents[row][col]+":");

    for (int i=firstRow; i<=lastRow; i++){
      for (int j=firstCol; j<=lastCol; j++){
        //System.out.print("["+agents[i][j]+"]");
        if (agents[i][j]=="X") countX++;
        else if (agents[i][j]=="O") countO++;
      }
      //System.out.println();
    }
    if(agents[row][col]=="X"){
      s=countX-1;
      d=countO;
    } else if (agents[row][col]=="O") {
      s=countO-1;
      d=countX;
    }
    //System.out.println("S: "+s+" --- D: "+d);

    if (s==0 && d==0) return true;
    else if (s / (s + d) >= threshold) return true;
    return false;
  }

  public static void moveAgentRandom(String[][] agents, int row, int col){
    if(agents[row][col]=="_") return;

    while(true){
      int randRow = rand.nextInt(agents.length);
      int randColumn = rand.nextInt(agents[0].length);
      if (agents[randRow][randColumn]=="_"){
        agents[randRow][randColumn] = agents[row][col];
        agents[row][col]="_";
        break;
      }
    }
  }

  public static void moveAgentClose(String[][] agents, int row, int col){
    if (agents[row][col]=="_") return;

    int firstRow, lastRow, firstCol, lastCol;
    
    if (row-1<0) firstRow=row;
    else firstRow=row-1;
    if (col-1<0) firstCol=col;
    else firstCol=col-1;
    if (row+1>=agents.length) lastRow=row;
    else lastRow=row+1;
    if (col+1>=agents.length) lastCol=col;
    else lastCol=col+1;

    if (agents[firstRow][col]=="_"){
      agents[firstRow][col] = agents[row][col];
      agents[row][col]="_";
      return;
    }
    for (int i=firstRow; i<=lastRow; i++){
      if (agents[i][lastCol]=="_"){
        agents[i][lastCol] = agents[row][col];
        agents[row][col]="_";
        return;
      }  
    }
    if (agents[lastRow][col]=="_"){
      agents[lastRow][col] = agents[row][col];
      agents[row][col]="_";
      return;
    }
    for (int i=lastRow; i>=firstRow; i--){
      if (agents[i][firstCol]=="_"){
        agents[i][firstCol] = agents[row][col];
        agents[row][col]="_";
        return;
      }  
    }
  }

  public static boolean allSatisfied(String[][] agents, double threshold){
    for (int row=0; row<agents.length; row++){
      for (int col=0; col<agents.length; col++){
        if (agents[row][col]!="_"){
          if (!isSatisfied(agents, row, col, threshold)) return false;
        }
      }
    }
    return true;
  }

  public static int simulateRandom(int size, int numsX, int numsO, double threshold){
    int countRounds=0;

    String[][] matrix = createBoard(size, numsX, numsO);
    printBoard(matrix);

    while(!allSatisfied(matrix, threshold)){

      for (int row=0; row<matrix.length; row++){
        for (int col=0; col<matrix.length; col++){
          if (matrix[row][col]!="_"){
            if (!isSatisfied(matrix, row, col, threshold)) moveAgentRandom(matrix, row, col);
          }
        }
      }
      printBoard(matrix);
      countRounds++;
    }
    return countRounds;
  }

  public static int simulateClosest(int size, int numsX, int numsO, double threshold){
    int countRounds=0;

    String[][] matrix = createBoard(size, numsX, numsO);
    printBoard(matrix);

    while(!allSatisfied(matrix, threshold)){

      for (int row=0; row<matrix.length; row++){
        for (int col=0; col<matrix.length; col++){
          if (matrix[row][col]!="_"){
            if (!isSatisfied(matrix, row, col, threshold)) moveAgentClose(matrix, row, col);
          }
        }
      }
      printBoard(matrix);
      countRounds++;
    }
    return countRounds;
  }

  
  
  public static void main (String[] args) {

    String[][] test = { 

      {"_", "_", "O", "_"},
      {"_", "X", "O", "X"},
      {"_", "_", "X", "O"},
      {"X", "_", "_", "X"}

    };

    /**test your functions below**/
    
    //String[][] matrix = createBoard(4, 5, 3);
    //printBoard(matrix);
    //System.out.println(isSatisfied(matrix, 3, 3, .5));

    //printBoard(test);
    //moveAgentRandom(test, 1, 1);
    //moveAgentClose(test , 3, 3);
    //printBoard(test);

    /* test = new String[][]{
      {"_", "O", "O", "O"}, 
      {"_", "_", "_", "_"}, 
      {"X", "_", "_", "X"}, 
      {"X", "_", "X", "X"}
    };
    System.out.println(allSatisfied(test, 0.5)); */

    //System.out.println("Rounds needed: "+simulateRandom(4, 4, 4, .5));
    System.out.println("Rounds needed: "+simulateClosest(5, 4, 4, .5));

  }
}