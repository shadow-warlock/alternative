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
        while(reader.isInputState()) {
            if(reader.get() == ')')
                return;
            if (reader.get() >= 'a' && reader.get() <= 'z') {
                String name = "" + reader.get();
                reader.read();
                while (reader.get() >= '0' && reader.get() <= '9'){
                    name += reader.get();
                    reader.read();
                }
                list.add(new Element(name, Type.VALUE, null));
                System.out.println(name);
                if(reader.get() == '&'){
                    System.out.println("AND");
                    list.add(new Element(null, Type.AND, null));
                    reader.read();
                    continue;
                }
                else if(reader.get() == '|'){
                    list.add(new Element(null, Type.OR, null));
                    System.out.println("OR");
                    reader.read();
                    continue;
                }
                else if(reader.get() == '='){
                    reader.read();
                    if(reader.get() == '>'){
                        list.add(new Element(null, Type.IMP, null));
                        System.out.println("IMP");
                        reader.read();
                        continue;
                    }
                    else {
                        System.out.println("Ошибка при попытке считать операцию");
                        throw new Exception();
                    }
                }
                else if(reader.isInputState()){
                    if(reader.get() == ')' && skCount == 0 || reader.get() != ')') {
                        System.out.println("Ошибка перехода после считывания переменной");
                        throw new Exception();
                    }
                }
                else{
                    System.out.println("END_INPUT");
                    return;
                }
            }
            else if(reader.get() == '!'){
                System.out.println("NOT");
                list.add(new Element(null, Type.NOT, null));
                reader.read();
                continue;
            }
            else if(reader.get() == '('){
                skCount++;
                System.out.println("OPEN");
                list.add(new Element(null, Type.OPEN, null));
                reader.read();
                startState();
                if(reader.get() == ')') {
                    skCount--;
                    list.add(new Element(null, Type.CLOSE, null));
                    System.out.println("CLOSE");
                    reader.read();
                    if(reader.isInputState()){
                        if(reader.get() == '&'){
                            System.out.println("AND");
                            list.add(new Element(null, Type.AND, null));
                            reader.read();
                            continue;
                        }
                        else if(reader.get() == '|'){
                            list.add(new Element(null, Type.OR, null));
                            System.out.println("OR");
                            reader.read();
                            continue;
                        }
                        else if(reader.get() == '='){
                            reader.read();
                            if(reader.get() == '>'){
                                list.add(new Element(null, Type.IMP, null));
                                System.out.println("IMP");
                                reader.read();
                                continue;
                            }
                            else {
                                System.out.println("Ошибка при попытке считать операцию");
                                throw new Exception();
                            }
                        }
                        else if(reader.get() != ')' || skCount == 0) {
                            System.out.println("Ошибка при попытке перейти к считыванию операции");
                            throw new Exception();
                        }
                    }else{
                        System.out.println("END_INPUT");
                        return;
                    }
                }else{
                    System.out.println("не найдена закрывающая скобка");
                    throw new Exception();
                }
            }
            else if(reader.get() == '1'){
                list.add(new Element(null, Type.CONST, true));
                System.out.println(true);
                reader.read();
                if(reader.get() == '&'){
                    System.out.println("AND");
                    list.add(new Element(null, Type.AND, null));
                    reader.read();
                    continue;
                }
                else if(reader.get() == '|'){
                    list.add(new Element(null, Type.OR, null));
                    System.out.println("OR");
                    reader.read();
                    continue;
                }
                else if(reader.get() == '='){
                    reader.read();
                    if(reader.get() == '>'){
                        list.add(new Element(null, Type.IMP, null));
                        System.out.println("IMP");
                        reader.read();
                        continue;
                    }
                    else {
                        System.out.println("Ошибка при попытке считать операцию");
                        throw new Exception();
                    }
                }
                else if(reader.isInputState()){
                    if(reader.get() == ')' && skCount == 0 || reader.get() != ')') {
                        System.out.println("Ошибка перехода после считывания переменной");
                        throw new Exception();
                    }
                }
                else{
                    System.out.println("END_INPUT");
                    return;
                }
            }
            else if(reader.get() == '0'){
                list.add(new Element(null, Type.CONST, false));
                System.out.println(false);
                reader.read();
                if(reader.get() == '&'){
                    System.out.println("AND");
                    list.add(new Element(null, Type.AND, null));
                    reader.read();
                    continue;
                }
                else if(reader.get() == '|'){
                    list.add(new Element(null, Type.OR, null));
                    System.out.println("OR");
                    reader.read();
                    continue;
                }
                else if(reader.get() == '='){
                    reader.read();
                    if(reader.get() == '>'){
                        list.add(new Element(null, Type.IMP, null));
                        System.out.println("IMP");
                        reader.read();
                        continue;
                    }
                    else {
                        System.out.println("Ошибка при попытке считать операцию");
                        throw new Exception();
                    }
                }
                else if(reader.isInputState()){
                    if(reader.get() == ')' && skCount == 0 || reader.get() != ')') {
                        System.out.println("Ошибка перехода после считывания переменной");
                        throw new Exception();
                    }
                }
                else{
                    System.out.println("END_INPUT");
                    return;
                }
            }
            else{
                System.out.println("ошибка в состоянии считывания скобки, отрицания, переменной или константы");
                throw new Exception();
            }
        }
        System.out.println("ошибка в состоянии считывания скобки, отрицания, переменной или константы");
        throw new Exception();
    }


    public ArrayList<Element> getRequest(){
        return list;
    }

}
