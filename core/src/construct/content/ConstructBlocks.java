package construct.content;

import arc.struct.Seq;
import construct.world.blocks.WeaponsDepot;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;

import static mindustry.type.ItemStack.with;

public class ConstructBlocks implements ContentList{
    public static Block
        //depots
        salvoDepot, artilleryDepot, missileDepot, laserBeamDepot, scatterDepot, fuseDepot, oilHoseDepot,
        //units
        chassisFactory, mediumChassisReconstructor, heavyChassisReconstructor;

    public void load(){
        //depots
        salvoDepot = new WeaponsDepot("salvo-depot"){ {
            requirements(Category.units, with(Items.copper, 300, Items.lead, 100, Items.silicon, 90, Items.graphite, 120, Items.titanium, 50));
            size = 2;
            update = true;
            arrayStart = 0;
            weaponType = 0;
            consumes.items(with(Items.copper, 30, Items.graphite, 40, Items.lead, 20));
            itemCapacity = 100;
        }
        };

        artilleryDepot = new WeaponsDepot("artillery-depot"){ {
            requirements(Category.units, with(Items.copper, 350, Items.lead, 300, Items.graphite, 500, Items.titanium, 500, Items.silicon, 550, Items.thorium, 600));
            size = 2;
            update = true;
            arrayStart = 2;
            weaponType = 2;
            consumes.items(with(Items.copper, 40, Items.graphite, 80, Items.titanium, 80, Items.metaglass, 40));
            itemCapacity = 160;
        }
        };

        missileDepot = new WeaponsDepot("missile-depot"){ {
            requirements(Category.units, with(Items.copper, 300, Items.lead, 200, Items.graphite, 150, Items.titanium, 300, Items.silicon, 350));
            size = 2;
            update = true;
            arrayStart = 5;
            weaponType = 0;
            consumes.items(with(Items.copper, 20, Items.graphite, 35, Items.titanium, 35, Items.silicon, 40));
            itemCapacity = 100;
        }
        };

        laserBeamDepot = new WeaponsDepot("laser-beam-depot"){ {
            requirements(Category.units, with(Items.copper, 250, Items.lead, 250, Items.graphite, 200, Items.titanium, 300, Items.silicon, 400, Items.metaglass, 80));
            size = 2;
            update = true;
            arrayStart = 7;
            weaponType = 2;
            consumes.items(with(Items.copper, 60, Items.lead, 70, Items.titanium, 80, Items.silicon, 50));
            itemCapacity = 180;
        }
        };

        scatterDepot = new WeaponsDepot("scatter-depot"){ {
            requirements(Category.units, with(Items.copper, 200, Items.lead, 200, Items.graphite, 120, Items.titanium, 50));
            size = 2;
            update = true;
            arrayStart = 10;
            weaponType = 0;
            consumes.items(with(Items.copper, 90, Items.lead, 50, Items.titanium, 20));
            itemCapacity = 200;
        }
        };

        fuseDepot = new WeaponsDepot("fuse-depot"){ {
            requirements(Category.units, with(Items.copper, 300, Items.graphite, 350, Items.titanium, 350, Items.silicon, 200, Items.thorium, 120));
            size = 2;
            update = true;
            arrayStart = 12;
            weaponType = 2;
            consumes.items(with(Items.copper, 250, Items.graphite, 250, Items.titanium, 120, Items.thorium, 120));
            itemCapacity = 500;
        }
        };

        oilHoseDepot = new WeaponsDepot("oil-hose-depot"){ {
            requirements(Category.units, with( Items.lead, 150, Items.titanium, 200, Items.silicon, 150, Items.metaglass, 100));
            size = 2;
            update = true;
            arrayStart = 15;
            weaponType = 0;
            consumes.items(with( Items.lead, 100, Items.titanium, 50, Items.metaglass, 40));
            consumes.liquid(Liquids.oil, 1);
            itemCapacity = 200;
            liquidCapacity = 40;
        }
        };


        //units
        chassisFactory = new UnitFactory("chassis-factory"){{
            requirements(Category.units, with(Items.copper, 150, Items.lead, 130, Items.metaglass, 120));
            plans = Seq.with(
                    new UnitPlan(ConstructUnits.lightChassis, 60f * 45f, with(Items.silicon, 45, Items.lead, 15))
            );
            size = 3;
            consumes.power(1.2f);
            floating = true;
        }};

        mediumChassisReconstructor = new Reconstructor("medium-chassis-reconstructor"){{
            requirements(Category.units, with(Items.lead, 650, Items.silicon, 450, Items.titanium, 350, Items.thorium, 650));

            size = 5;
            consumes.power(6f);
            consumes.items(with(Items.silicon, 130, Items.titanium, 80, Items.metaglass, 40));

            constructTime = 60f * 30f;

            upgrades.addAll(
                    new UnitType[]{ConstructUnits.lightChassis, ConstructUnits.mediumChassis}
            );
        }};


        heavyChassisReconstructor = new Reconstructor("heavy-chassis-reconstructor"){{
            requirements(Category.units, with(Items.lead, 2000, Items.silicon, 1000, Items.titanium, 2000, Items.thorium, 750, Items.plastanium, 450, Items.phaseFabric, 600));

            size = 7;
            consumes.power(13f);
            consumes.items(with(Items.silicon, 850, Items.titanium, 750, Items.plastanium, 650));
            consumes.liquid(Liquids.cryofluid, 1f);

            constructTime = 60f * 60f * 1.5f;
            liquidCapacity = 60f;

            upgrades.addAll(
                    new UnitType[]{ConstructUnits.mediumChassis, ConstructUnits.heavyChassis}
            );
        }};
    }
}
