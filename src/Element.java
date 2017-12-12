/**
 * Created by root on 08.12.17.
 */
enum Type{
    VALUE,
    AND,
    OR,
    IMP,
    CONST,
    NOT,
    OPEN,
    CLOSE
}
public class Element{
    Type type;
    String name;
    Boolean value;
    public Element(String name, Type type, Boolean value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        if(name != null)
            return name;
        if(value != null)
            return "" + value;
        return "" + type;
    }
}
