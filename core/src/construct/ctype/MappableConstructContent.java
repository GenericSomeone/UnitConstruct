//this seems important
package construct.ctype;

import construct.Varsr;

public abstract class MappableConstructContent extends ConstructContent {

    public String name;

    public MappableConstructContent(String name){
        this.name = name;
        Varsr.content.handleContent(this);
    }
}
