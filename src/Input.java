import java.util.ArrayList;

/**
 * Created by root on 08.12.17.
 */
public class Input {
    public static void main(String[] args) throws Exception {
        Automaton automaton = new Automaton();
        automaton.start();
        ArrayList<Element> list = automaton.getRequest();
//        for (Element e:list) {
//            System.out.println(e);
//        }
        LogicTree l = new LogicTree(list);
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRR \n");
        l.out();
    }







}
