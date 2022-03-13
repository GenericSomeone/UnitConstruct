//stuff that I yanked from Endless Rusting
package construct.entities.units;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.entities.abilities.Ability;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import construct.graphics.Drawr;

import static mindustry.Vars.player;

public class AutospriteUnitType extends UnitType {
    public static final float shadowTX = -12, shadowTY = -13;
    private static final Vec2 legOffset = new Vec2();


    public AutospriteUnitType(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        Drawr.fullSpriteGenerator(this);
    }


    public void draw(Unit unit){
        Mechc mech = !flying && unit instanceof Mechc ? (Mechc)unit : null;
        float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);

        if(unit.controller().isBeingControlled(player.unit())){
            drawControl(unit);
        }

        if(unit.isFlying() || visualElevation > 0){
            Draw.z(Math.min(Layer.darkness, z - 1f));
            drawShadow(unit);
        }

        Draw.z(z - 0.02f);

        if(mech != null){
            drawMech(mech);

            //side
            legOffset.trns(mech.baseRotation(), 0f, Mathf.lerp(Mathf.sin(mech.walkExtend(true), 2f/Mathf.PI, 1) * mechSideSway, 0f, unit.elevation));

            //front
            legOffset.add(Tmp.v1.trns(mech.baseRotation() + 90, 0f, Mathf.lerp(Mathf.sin(mech.walkExtend(true), 1f/Mathf.PI, 1) * mechFrontSway, 0f, unit.elevation)));

            unit.trns(legOffset.x, legOffset.y);
        }

        if(unit instanceof Legsc){
            drawLegs((Unit & Legsc)unit);
        }

        Draw.z(Math.min(z - 0.01f, Layer.bullet - 1f));

        if(unit instanceof Payloadc){
            drawPayload((Unit & Payloadc)unit);
        }

        drawSoftShadow(unit);

        Draw.z(z);

        drawOutline(unit);
        //drawWeaponOutlines(unit);
        if(engineSize > 0) drawEngine(unit);
        drawBody(unit);
        if(drawCell) drawCell(unit);
        drawWeapons(unit);
        if(drawItems) drawItems(unit);
        drawLight(unit);

        if(unit.shieldAlpha > 0 && drawShields){
            drawShield(unit);
        }

        if(mech != null){
            unit.trns(-legOffset.x, -legOffset.y);
        }

        if(unit.abilities.size > 0){
            for(Ability a : unit.abilities){
                Draw.reset();
                a.draw(unit);
            }

            Draw.reset();
        }
    }
}
