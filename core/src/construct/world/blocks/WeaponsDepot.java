package construct.world.blocks;

import arc.Core;
import arc.scene.ui.layout.*;
import construct.entities.units.ChassisUnitEntity;
import mindustry.*;
import mindustry.entities.Units;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.world.*;
import mindustry.world.meta.*;


import static mindustry.Vars.*;

public class WeaponsDepot extends Block{
    public int powerUsage = 1;
    public float serviceRange = 8 * 10;

    //determines the type of weapon that the depot uses; 0 is secondary, 2 is primary
    public int weaponType;

    //determines the id that it should start taking weapons from
    public int arrayStart;

    public WeaponsDepot(String name){
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        configurable = true;
        group = BlockGroup.units;
    }

    //depot power requirements coming maybe
    /*@Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.range, serviceRange / 8, StatUnit.blocks);
        stats.add(Stat.powerUse, powerUsage * 60, StatUnit.perSecond);
    }*/

    //standard drawPlace stuff
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        Drawf.circles(x*tilesize + offset, y*tilesize + offset, serviceRange);
    }

    public class WeaponsDepotBuild extends Building implements Ranged {

        @Override
        public void buildConfiguration(Table table) {
            Table buttons = new Table();
            int numberOfButtons = 2;
            //add an extra button for primaries
            if (weaponType == 2) {numberOfButtons = 3;}
            for (int i = 0; i < numberOfButtons; i++) {
                //need to make a final variable because it won't be accepted otherwise
                int finalI = i;
                buttons.button(String.valueOf(i + 1), () -> {
                    Unit unit = Units.closest(this.team, x, y, serviceRange * 2, u -> u instanceof ChassisUnitEntity);
                    //although all depots have item costs, this is implemented anyway to allow them to be used without if one wishes to configure them that way
                    if ((unit != null && unit.team == this.team && (consValid() || this.block.hasItems == false && this.block.hasLiquids == false) && weaponType + finalI <= unit.mounts().length - 1)) {
                        ((ChassisUnitEntity) unit).getNewWeapon(weaponType + finalI, arrayStart + finalI);
                        consume();
                    //detects why the weapon cannot be mounted and returns the error accordingly
                    } else if (weaponType + finalI > unit.mounts().length - 1) {
                        Vars.ui.showInfoToast(Core.bundle.get("invalid-slot-error-message"), 3);
                    } else {
                        Vars.ui.showInfoToast(Core.bundle.get("insufficient-resource-error-message"), 3);
                    }
                }).size(40);
            }
            table.add(buttons);

        }

        //more average click-to-use building stuff
        @Override
        public boolean onConfigureTileTapped(Building other) {
            if (this == other) {
                deselect();
                return false;
            }
            return super.onConfigureTileTapped(other);
        }


        @Override
        public void drawConfigure() {
            super.drawConfigure();
            Drawf.circles(x, y, serviceRange);
        }

        @Override
        public float range() {
            return serviceRange;
        }

        //I have no idea what this does
        @Override
        public byte version() {
            return 1;
        }
    }
}