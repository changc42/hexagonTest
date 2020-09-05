import java.util.*;

class Circuit{
    String name;
    int h;
    int e;
    int p;
    LinkedList<Juggler> assigned;

    public Circuit(String n, int hSkill, int eSkill, int pSkill){
        name = n;
        h = hSkill;
        e = eSkill;
        p = pSkill;
        assigned = new LinkedList<Juggler>();
    }

    public String toAnswerFormat(){
        assigned.sort((Juggler j1, Juggler j2) -> j2.scores.get(name) - j1.scores.get(name));
        
        String answer = "";
        answer += name + " ";
        for(Juggler j: assigned){
            answer += j.name + " ";
            for(String circuitName : j.preferences){
                answer += circuitName + ":" + j.scores.get(circuitName) + " ";
            }
            answer = answer.substring(0, answer.length()-1);
            answer += ", ";
        }
        answer = answer.substring(0, answer.length()-2);
        return answer;
    }

    public String toString(){
        String answer = "";
        answer += String.format("name:%s, assigned:", name);
        for(Juggler j : assigned){
            answer += j.name + ", ";
        }
        return answer;
    }

    public Juggler getWorstJuggler(){
        if(assigned.size()==0) return null;

        Juggler worst = assigned.get(0);
        for(Juggler j: assigned){
            if(j.scores.get(name) < worst.scores.get(name)) worst = j;
        }

        return worst;
    }

    public void removeWorstJuggler(){
        Juggler j = getWorstJuggler();
        assigned.remove(j);
    }

    public void assignJuggler(Juggler j){
        assigned.add(j);
    }
}