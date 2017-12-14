import java.util.ArrayList;

class LogicTree {
    private Node root;

    void out(){
        out(root);
    }

    private void out(Node e) {
        if(e == null)
            return;
        if (e.type != Type.NOT) {
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
    private static boolean isOperation(Type type){
        return (type == Type.IMP) || (type == Type.OR) || (type == Type.AND) || (type == Type.NOT);
    }

    private static boolean lowPriority(Type currOperation, Type operation){ //возвращает истину,
       if(!isOperation(currOperation)){                                      //если первый параметр менее приоритетен,
           return false;                                                     //чем второй
       }
       if(operation==Type.NOT){
           return true;
       }
       if(currOperation == Type.IMP){
           return true;
       }
       if((currOperation==Type.OR)&&(operation!=Type.IMP)){
           return true;
       }
       if((currOperation==Type.AND)&&(operation!=Type.OR)&&(operation!=Type.IMP)){
           return true;
       }
       return false;
    }

    private Node add(ArrayList<Element> logStr){
        killScobka(logStr);
        Type type = logStr.get(0).type;
        if(logStr.size()<=2){
            int vInd = logStr.size()-1;
            Node op = new Node(logStr.get(vInd).value,null,null,logStr.get(vInd).type,logStr.get(vInd).name);
            if(type==Type.NOT){
//                if(killNotBeforeConst(logStr, 0))
//                    return add(logStr);
                return new Node(null,op,null,Type.NOT,null);
            }
            else{
                return op;
            }
        }
        else{
            int scobe=0;
            int currLowPriority = -1;
            Type currLowType = Type.NOT;
            for (int i = 0; i < logStr.size(); i++) {
                type = logStr.get(i).type;
                if(currLowPriority!=-1){
                    currLowType = logStr.get(currLowPriority).type;
                }
                if (logStr.get(i).type == Type.OPEN) {
                    scobe++;
                    continue;
                }
                if (logStr.get(i).type == Type.CLOSE) {
                    scobe--;
                    continue;
                }
                if (scobe == 0) {
                    if (isOperation(type)) {
                        if(lowPriority(type,currLowType)){
                            currLowPriority=i;
                        }
                    }
                }
            }
//            if(currLowPriority != -1) {
//                if (killMoreThenOneNot(logStr, currLowPriority))
//                    return add(logStr);
//                if (killNotBeforeConst(logStr, currLowPriority))
//                    return add(logStr);
//                if (killOrWithConst(logStr, currLowPriority))
//                    return add(logStr);
//            }

            ArrayList<Element> logRight = new ArrayList<>(logStr.subList(currLowPriority+1,logStr.size()));
            if(currLowType==Type.NOT){
                return new Node(null,add(logRight),null,currLowType,null);
            }
            else{
                ArrayList<Element> logLeft = new ArrayList<>(logStr.subList(0,currLowPriority));
                return new Node(null,add(logLeft),add(logRight),currLowType,null);
            }
        }
    }

//    static ArrayList<Element> getSubLeft(ArrayList<Element> logStr, int rightNotIncl){
//        ArrayList<Element> req = new ArrayList<>();
//        if(logStr.get(rightNotIncl-1).type == Type.CONST || logStr.get(rightNotIncl-1).type == Type.VALUE){
//            req.add(logStr.get(rightNotIncl-1));
//        }
//        if(logStr.get(rightNotIncl-1).type == Type.CLOSE){
//            req.add(logStr.get(rightNotIncl-1));
//            int skLvl = 1;
//            int id = rightNotIncl-2;
//            while (skLvl != 0){
//                if (logStr.get(id).type == Type.CLOSE)
//                    skLvl++;
//                if(logStr.get(id).type == Type.OPEN)
//                    skLvl--;
//                req.add(logStr.get(id));
//                id--;
//            }
//        }
//        return req;
//    }
//
//    static ArrayList<Element> getSubRight(ArrayList<Element> logStr, int leftNotIncl){
//        ArrayList<Element> req = new ArrayList<>();
//        if(logStr.get(leftNotIncl+1).type == Type.CONST || logStr.get(leftNotIncl+1).type == Type.VALUE){
//            req.add(logStr.get(leftNotIncl+1));
//        }
//        if(logStr.get(leftNotIncl+1).type == Type.OPEN){
//            req.add(logStr.get(leftNotIncl+1));
//            int skLvl = 1;
//            int id = leftNotIncl+2;
//            while (skLvl != 0){
//                if (logStr.get(id).type == Type.OPEN)
//                    skLvl++;
//                if(logStr.get(id).type == Type.CLOSE)
//                    skLvl--;
//                req.add(logStr.get(id));
//                id++;
//            }
//        }
//        return req;
//    }
//
//    static boolean killOrWithConst(ArrayList<Element> logStr, int or){
//        System.out.println("KILLLLL OR CONST");
//        if(logStr.get(or).type == Type.OR) {
//            if(logStr.get(or+1).type == Type.CONST){
//                if(logStr.get(or+1).value){
//                    logStr.remove(or);
//                    for (Element e : getSubLeft(logStr, or))
//                        logStr.remove(e);
//                }else {
//                    logStr.remove(or+1);
//                    logStr.remove(or);
//                }
//                return true;
//            }
//            else if(logStr.get(or-1).type == Type.CONST){
//                if(logStr.get(or-1).value){
//                    for (Element e : getSubRight(logStr, or))
//                        logStr.remove(e);
//                    logStr.remove(or);
//                }else {
//                    logStr.remove(or);
//                    logStr.remove(or-1);
//                    System.out.println(logStr);
//                }
//                return true;
//            }
//            return false;
//        }else{
//            return false;
//        }
//    }
//
//    static boolean killNotBeforeConst(ArrayList<Element> logStr, int not){
//        System.out.println("KILLLLL NOT CONST");
//        if(logStr.get(not).type == Type.NOT && logStr.get(not+1).type == Type.CONST) {
//            if(logStr.get(not+1).value){
//                logStr.get(not+1).value = false;
//                logStr.remove(not);
//            }else{
//                logStr.get(not+1).value = true;
//                logStr.remove(not);
//            }
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    static boolean killMoreThenOneNot(ArrayList<Element> logStr, int rightNot){
//        System.out.println("KILLLLL NOT");
//        if(rightNot > 0 && logStr.get(rightNot).type == Type.NOT && logStr.get(rightNot-1).type == Type.NOT) {
//            while (rightNot > 0 && logStr.get(rightNot).type == Type.NOT && logStr.get(rightNot - 1).type == Type.NOT) {
//                logStr.remove(rightNot);
//                logStr.remove(rightNot - 1);
//                rightNot -= 2;
//            }
//            return true;
//        }else{
//            return false;
//        }
//    }

     static void killScobka(ArrayList<Element> arrayList){
        if(arrayList.size() > 0 && arrayList.get(0).type == Type.OPEN){
            int skLvl = 0;
            for (Element o : arrayList){
                if(o.type == Type.CLOSE)
                    skLvl--;
                if(o.type == Type.OPEN)
                    skLvl++;
                if(skLvl == 0 && arrayList.indexOf(o) != arrayList.size()-1) {
                    return;
                }
            }
            arrayList.remove(arrayList.size()-1);
            arrayList.remove(0);
        }
    }

    LogicTree(ArrayList<Element> logStr){
        root = add(logStr);
    }

    private class Node{
        Boolean value;
        Node leftOperand;
        Node rightOperand;
        Type type;
        String name;

        Node(Boolean value,Node leftNode,Node rightNode,Type type,String name){
            this.value = value;
            this.leftOperand = leftNode;
            this.rightOperand = rightNode;
            this.type = type;
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
                request = " " + type + " ";
            }
            return request;
        }
    }
}

