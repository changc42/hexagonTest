import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

class Main{
    static HashMap<String, Circuit> circuits;
    static ArrayList<Juggler> jugglers;
    static int jugglersPerCurcuit;

    public static boolean isEveryoneAssigned(){
        for(Juggler j: jugglers){
            if(!j.isAssigned) return false;
        }
        return true;
    }

    public static void apply(Juggler j){
        
        String nextPreference = j.nextPreference();
        Circuit c;
        if(nextPreference==null){
            c = getIncompleteCircuit();
        }else{
            c = circuits.get(nextPreference);
        }
        
        j.applications.add(c.name);

        Juggler worstJuggler = c.getWorstJuggler();

        if(c.assigned.size()<jugglersPerCurcuit){
            c.assignJuggler(j);
            j.isAssigned = true;
        }else if( j.scores.get(c.name) > worstJuggler.scores.get(c.name) ){
            c.removeWorstJuggler();
            worstJuggler.isAssigned = false;
            c.assignJuggler(j);
            j.isAssigned = true;
        }
    }

    public static Circuit getIncompleteCircuit(){
        for(Map.Entry<String, Circuit> entry: circuits.entrySet()){
            if(entry.getValue().assigned.size() < jugglersPerCurcuit) return entry.getValue();
        }
        return null;
    }

    public static void main(String[] args){
        circuits = new HashMap<String, Circuit>();
        jugglers = new ArrayList<Juggler>();
        String file = "jugglers.txt";
        try{

            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.equals("")) continue;
                String[] dataArr = data.split(" ",0);

                String name = dataArr[1];
                int h = Integer.parseInt(dataArr[2].split(":", 0)[1]);
                int e = Integer.parseInt(dataArr[3].split(":", 0)[1]);
                int p = Integer.parseInt(dataArr[4].split(":", 0)[1]);

                if(dataArr[0].equals("C")){
                    Circuit circuit = new Circuit(name, h, e, p);
                    circuits.put(name, circuit);
                }else if(dataArr[0].equals("J")){
                    String[] preferences = dataArr[5].split(",", 0);
                    Juggler juggler = new Juggler(name, h, e, p, preferences);
                    jugglers.add(juggler);
                }
            }

            myReader.close();

        }catch(FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //find jugglers per circuit
        int totalCircuits = circuits.entrySet().size();
        jugglersPerCurcuit = jugglers.size()/totalCircuits;

        for(Juggler j : jugglers){
            for(Map.Entry<String, Circuit> entry: circuits.entrySet()){
                Circuit c = entry.getValue();
                j.scores.put(c.name, c.h*j.h + c.e*j.e + c.p*j.p);
            }
        }

        while(!isEveryoneAssigned()){
            for(Juggler j: jugglers){
                while(!j.isAssigned){
                    apply(j);
                }
            }
        }

        try {
            File myObj = new File("output.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

      
        try {
            FileWriter myWriter = new FileWriter("output.txt");
            for(Map.Entry<String, Circuit> entry: circuits.entrySet()){
                myWriter.write(entry.getValue().toAnswerFormat() + "\n");
            }
            
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    
        //   for(Map.Entry<String, Circuit> c: circuits){

    }
}