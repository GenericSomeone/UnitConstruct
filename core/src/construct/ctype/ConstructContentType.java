//this seems important
package construct.ctype;
public class ConstructContentType {


    public int ordinal = 0;
    private static int nextFreeOrdinal = 0;

    public ConstructContentType(String name){
        this.name = name;
        this.ordinal = nextFreeOrdinal;
        nextFreeOrdinal++;
    }

    //how you find the ContentType
    public String name = "UNUSED";

    public String name(){
        return name;
    }

    public String toString(){
        return "ConstructContentType#" + ordinal;
    }
}
