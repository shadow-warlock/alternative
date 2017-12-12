import java.util.ArrayList;

public class LogicTree {
    private Operand root;

    private Operand add(ArrayList<Element> logStr){
        Type operation;
        int scobe=0;
        int currLowPriority = 0;
        if(logStr.size()==2){
            if(logStr.get(0).type==Type.NOT){
                return new Operand(null, add(new ArrayList<Element>(logStr.subList(1,2))), null, Type.NOT, null);
            }
            else {
                System.out.println("magick");
                return null;
            }
        }else if(logStr.size()==1){
            return new Operand(logStr.get(0).value, null, null, logStr.get(0).type, logStr.get(0).name);
        }
        else{
            for (int i = 0; i < logStr.size(); i++) {
                operation = logStr.get(i).type;
                if (logStr.get(i).type == Type.OPEN) {
                    scobe++;
                    continue;
                }
                if (logStr.get(i).type == Type.CLOSE) {
                    scobe--;
                    continue;
                }
                if (scobe == 0) {
                    if (operation == Type.IMP) {
                        currLowPriority = i;
                    } else {
                        if ((operation == Type.OR) && (logStr.get(currLowPriority).type != Type.IMP)) {
                            currLowPriority = i;
                        } else {
                            if (currLowPriority == 0 || ((operation == Type.AND) && (logStr.get(currLowPriority).type == Type.AND))) {
                                currLowPriority = i;
                            }
                        }
                    }
                }
            }
            ArrayList<Element> logLeft=killScobka(new ArrayList<Element>(logStr.subList(0,currLowPriority)));
            ArrayList<Element> logRight=killScobka(new ArrayList<Element>(logStr.subList(currLowPriority+1,logStr.size())));
            return new Operand(null,add(logLeft),add(logRight),logStr.get(currLowPriority).type,null);
        }
    }

    private void isNot(ArrayList<Element> logStr){

    }

    private ArrayList<Element> killScobka(ArrayList<Element> arrayList){
        if(arrayList.size() > 0 && arrayList.get(0).type == Type.OPEN){
            int skLvl = 0;
            for (Element o : arrayList){
                if(o.type == Type.CLOSE)
                    skLvl--;
                if(o.type == Type.OPEN)
                    skLvl++;
                if(skLvl == 0 && arrayList.indexOf(o) != arrayList.size()-1) {
                    return arrayList;
                }
            }
            System.out.println("***********************************");
            System.out.println(arrayList);
            System.out.println("killSkobka");
            arrayList.remove(arrayList.size()-1);
            arrayList.remove(0);
            System.out.println(arrayList);
            System.out.println("***********************************");
        }
        return arrayList;
    }

    public LogicTree(ArrayList<Element> logStr){
        killScobka(logStr);
        root = add(logStr);
    }

    private class Operand{
        Boolean value;
        Operand leftOperand;
        Operand rightOperand;
        Type operation;
        String name;

        Operand(Boolean value,Operand leftOperand,Operand rightOperand,Type operation,String name){
            this.value = value;
            this.leftOperand = leftOperand;
            this.rightOperand = rightOperand;
            this.operation = operation;
            this.name = name;
        }

        @Override
        public String toString() {
            String request = "";
            if(value != null)
                request += value;
            else if (name != null)
                request += name;
            else{
                request = " " +operation + " ";
            }
            return request;
        }
    }

    public void out(){
        out(root);
    }

    private void out(Operand e) {
        if (e.operation != Type.NOT) {
            if (e.leftOperand != null || e.rightOperand != null)
                System.out.print("(");
            if (e.leftOperand != null) {
                out(e.leftOperand);
            }
            System.out.print(e);
            if (e.rightOperand != null) {
                out(e.rightOperand);
            }
            if (e.leftOperand != null || e.rightOperand != null)
                System.out.print(")");
        }else{
            System.out.print("NOT ");
            out(e.leftOperand);
        }
    }

}

