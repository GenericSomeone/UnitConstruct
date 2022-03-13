package construct.entities.units;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.UnitEntity;
import construct.content.ConstructUnits;

public class ChassisUnitEntity extends UnitEntity {

    public ChassisUnitType unitType(){ return type instanceof ChassisUnitType ? (ChassisUnitType) type : null; }

    //stuff used for saving
    public int mountCount;
    public int[] weaponSaveArray;

    //mounts the specified weapon in the parameter when called
    public void getNewWeapon (int targetWeapon, int weaponID){
        if (targetWeapon <= this.mounts().length - 1) {
            this.mounts[targetWeapon] = new WeaponMount(ConstructUnits.weaponsList.weapons.get(weaponID));

            //write the weapon ID in an array
            this.weaponSaveArray[targetWeapon] = weaponID;
        } /*else {
            //shows an error message if slot is invalid
            Vars.ui.showInfoToast(Core.bundle.get("invalid-slot-error-message"), 3);
        }*/
    }

    //used to make the weaponSaveArray the same length as the mounts array
    public void initializeWeaponSaveArray(){
        //we need to save this value because for some reason this.mounts.length on startup simply returns 0
        if (this.mounts.length != 0) {
            mountCount = this.mounts.length;
        }

        //expand the weapon array to whatever the weapon capacity is
        int[] overrideWeaponSaveArray = new int[mountCount];
        this.weaponSaveArray = overrideWeaponSaveArray;

        //this value is just a placeholder to prevent the slots in the array from being null; anything less than 0 should work
        for (int i = 0; i < this.mounts().length; i++) {
            this.weaponSaveArray[i] = -1;
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.weaponSaveArray == null) {
            initializeWeaponSaveArray();
        }
    }

    @Override
    public void draw(){
        super.draw();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void write(Writes write) {
        super.write(write);
        write.i(mountCount);
        if (this.weaponSaveArray != null) {
            for (int i = 0; i < this.mounts().length; i++) {
                write.i(this.weaponSaveArray[i]);
            }
        }
    }

    @Override
    public void read(Reads read){
        super.read(read);
        mountCount = read.i();
        initializeWeaponSaveArray();
        for (int i = 0; i < mountCount; i++) {
            this.weaponSaveArray[i] = read.i();
            //restores weapons based on IDs in the weaponSaveArray; will ignore any IDs below 0
            if (this.weaponSaveArray[i] >= 0) {
                getNewWeapon(i, this.weaponSaveArray[i]);
            }
        }
    }

    @Override
    public int classId(){
        return ConstructUnits.classID(ChassisUnitEntity.class);
    }

}
