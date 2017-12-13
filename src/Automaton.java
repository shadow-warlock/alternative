import java.util.ArrayList;

/**
 * Created by root on 11.12.17.
 */
public class Automaton {
    private ArrayList<Element> list = new ArrayList<>();
    private MyReader reader = new MyReader();
    private int skCount = 0;

    public void start() throws Exception {
        reader.read();
        startState();
    }

    private void startState() throws Exception {
        if(reader.isInputState()) {
            if (reader.get() >= 'a' && reader.get() <= 'z') {
                letter();
            }
            else if(reader.get() == '!'){
                not();
            }
            else if(reader.get() == '('){
                open();
            }
            else if(reader.get() == '1'){
                one();
            }
            else if(reader.get() == '0'){
                zero();
            }
            else{
                System.out.println("ошибка в состоянии считывания скобки, отрицания, переменной или константы");
                throw new Exception();
            }
        }else{
            System.out.println("ошибка в состоянии считывания скобки, отрицания, переменной или константы");
            throw new Exception();
        }
    }

    private void not() throws Exception {
        System.out.println("NOT");
        list.add(new Element(null, Type.NOT, null));
        reader.read();
        startState();
    }
    private void open() throws Exception {
        skCount++;
        System.out.println("OPEN");
        list.add(new Element(null, Type.OPEN, null));
        reader.read();
        startState();
        close();
    }
    private void close() throws Exception {
        if(reader.get() == ')') {
            skCount--;
            list.add(new Element(null, Type.CLOSE, null));
            System.out.println("CLOSE");
            reader.read();
            if(reader.isInputState()){
                if(reader.get() == '&'){
                    and();
                }
                else if(reader.get() == '|'){
                    or();
                }
                else if(reader.get() == '='){
                    imp();
                }
                else if(reader.get() != ')' || skCount == 0) {
                    System.out.println("Ошибка при попытке перейти к считыванию операции");
                    throw new Exception();
                }
            }else{
                System.out.println("END_INPUT");
            }
        }else{
            System.out.println("не найдена закрывающая скобка");
            throw new Exception();
        }
    }

    private void letter() throws Exception {
        String name = "" + reader.get();
        reader.read();
        while (reader.get() >= '0' && reader.get() <= '9'){
            name += reader.get();
            reader.read();
        }
        list.add(new Element(name, Type.VALUE, null));
        System.out.println(name);
        if(reader.get() == '&'){
            and();
        }
        else if(reader.get() == '|'){
            or();
        }
        else if(reader.get() == '='){
            imp();
        }
        else if(reader.isInputState()){
            if(reader.get() == ')' && skCount == 0 || reader.get() != ')') {
                System.out.println("Ошибка перехода после считывания переменной");
                throw new Exception();
            }
        }
        else{
            System.out.println("END_INPUT");
        }
    }

    private void and() throws Exception {
        System.out.println("AND");
        list.add(new Element(null, Type.AND, null));
        reader.read();
        startState();
    }

    private void or() throws Exception {
        list.add(new Element(null, Type.OR, null));
        System.out.println("OR");
        reader.read();
        startState();
    }
    private void imp() throws Exception {
        reader.read();
        if(reader.get() == '>'){
            list.add(new Element(null, Type.IMP, null));
            System.out.println("IMP");
            reader.read();
            startState();
        }
        else {
            System.out.println("Ошибка при попытке считать операцию");
            throw new Exception();
        }
    }


    private void one() throws Exception {
        list.add(new Element(null, Type.CONST, true));
        System.out.println(true);
        reader.read();
        if(reader.get() == '&'){
            and();
        }
        else if(reader.get() == '|'){
            or();
        }
        else if(reader.get() == '='){
            imp();
        }
        else if(reader.isInputState()){
            if(reader.get() == ')' && skCount == 0 || reader.get() != ')') {
                System.out.println("Ошибка перехода после считывания переменной");
                throw new Exception();
            }
        }
        else{
            System.out.println("END_INPUT");
        }
    }
    private void zero() throws Exception {
        list.add(new Element(null, Type.CONST, false));
        System.out.println(false);
        reader.read();
        if(reader.get() == '&'){
            and();
        }
        else if(reader.get() == '|'){
            or();
        }
        else if(reader.get() == '='){
            imp();
        }
        else if(reader.isInputState()){
            if(reader.get() == ')' && skCount == 0 || reader.get() != ')') {
                System.out.println("Ошибка перехода после считывания переменной");
                throw new Exception();
            }
        }
        else{
            System.out.println("END_INPUT");
        }
    }

    public ArrayList<Element> getRequest(){
        return list;
    }

}
