import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Main{

    static ArrayList<ArrayList<Integer>> triangle;
    static int totalLines;
    static int max;
    static HashMap<String, Integer> dp;

    public static int getMaxOfNode(int currLine, int currPos){
        // System.out.printf("currline:%d, currPos:%d \n", currLine, currPos);
        String key = Integer.toString(currLine) + "," + Integer.toString(currPos);
        if(dp.containsKey(key)) return dp.get(key);
        else{
            if(currLine==totalLines-1){
                return triangle.get(currLine).get(currPos);
            }

            int maxOfNode = triangle.get(currLine).get(currPos) + Math.max(getMaxOfNode(currLine+1, currPos), getMaxOfNode(currLine+1, currPos+1));
            dp.put(key, maxOfNode);
            return maxOfNode;
        }
    }

    public static void main(String args[]){
        String file = "triangle.txt";
        try{
            dp = new HashMap<String, Integer>();
            triangle = new ArrayList<ArrayList<Integer>>();
            totalLines = 0;
            max = Integer.MIN_VALUE;

            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                totalLines++;

                ArrayList<Integer> line = new ArrayList<Integer>();
                String data = myReader.nextLine();
                String[] dataArr = data.split(" ", 0);
                for(int i=0; i<dataArr.length; i++){
                    line.add(Integer.parseInt(dataArr[i]));
                }
                triangle.add(line);
            }

            myReader.close();

            System.out.println(getMaxOfNode(0,0));

        }catch(FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}