package construct;

import mindustry.mod.Mod;
import construct.graphics.Drawr;

public class UnitConstruct extends Mod{

    public static String modname = "unit-construct";

    @Override
    public void loadContent(){
        Drawr.setMethods();
        Varsr.content.createContent();
    }
}
