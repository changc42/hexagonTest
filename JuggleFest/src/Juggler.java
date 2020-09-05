import java.util.*;

class Juggler{
    String name;
    int h;
    int e;
    int p;
    String[] preferences;
    HashMap<String, Integer> scores;
    HashSet<String> applications;
    boolean isAssigned;

    public Juggler(String n, int hSkill, int eSkill, int pSkill, String[] pref){
        name = n;
        h = hSkill;
        e = eSkill;
        p = pSkill;
        preferences = pref;
        scores = new HashMap<String, Integer>();
        applications = new HashSet<String>();
        isAssigned = false;
    }

    public String toString(){
        String answer = "";
        answer += String.format("name:%s, h:%d, e:%d, p:%d\npreferences:\n", name, h, e, p);
        for(String s: preferences){
            answer += String.format("%s:%d\n",s, scores.get(s));
        }
        return answer;
    }

    public String nextPreference(){
        for(String s: preferences){
            if(!applications.contains(s)) return s;
        }
        return null;
    }
}