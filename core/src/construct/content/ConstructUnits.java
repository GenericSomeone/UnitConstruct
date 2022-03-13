package construct.content;

import arc.func.Prov;
import arc.graphics.Color;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap.Entry;
import mindustry.ai.types.BuilderAI;
import mindustry.ai.types.FlyingAI;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.Weapon;
import construct.entities.units.*;


public class ConstructUnits implements ContentList{
    //Stolen from Endless Rusting, which was stolen from BetaMindy
    private static Entry<Class<? extends Entityc>, Prov<? extends Entityc>>[] types = new Entry[]{
            prov(ChassisUnitEntity.class, ChassisUnitEntity::new),
    };

    private static ObjectIntMap<Class<? extends Entityc>> idMap = new ObjectIntMap<>();

    /**
     * Internal function to flatmap {@code Class -> Prov} into an {@link Entry}.
     * @author GlennFolker
     */
    private static <T extends Entityc> Entry<Class<T>, Prov<T>> prov(Class<T> type, Prov<T> prov){
        Entry<Class<T>, Prov<T>> entry = new Entry<>();
        entry.key = type;
        entry.value = prov;
        return entry;
    }

    /**
     * Setups all entity IDs and maps them into {@link EntityMapping}.
     * @author GlennFolker
     */

    private static void setupID(){
        for(
                int i = 0,
                j = 0,
                len = EntityMapping.idMap.length;

                i < len;

                i++
        ){
            if(EntityMapping.idMap[i] == null){
                idMap.put(types[j].key, i);
                EntityMapping.idMap[i] = types[j].value;

                if(++j >= types.length) break;
            }
        }
    }

    /**
     * Retrieves the class ID for a certain entity type.
     * @author GlennFolker
     */
    public static <T extends Entityc> int classID(Class<T> type){
        return idMap.get(type, -1);
    }

    //gamma weapons are used as placeholders
    public Weapon placeholderWeapon = new Weapon("unit-construct-placeholder-weapon") {{
        top = false;
        rotate = true;
        reload = 15f;
        x = 0f;
        y = 0f;
        shots = 2;
        spacing = 2f;
        inaccuracy = 3f;
        shotDelay = 3f;
        ejectEffect = Fx.casing1;
        mirror = false;

        bullet = new BasicBulletType(3.5f, 11) {{
            width = 6.5f;
            height = 11f;
            lifetime = 70f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            buildingDamageMultiplier = 0.01f;
            homingPower = 0.04f;
        }};
    }};

    public static ChassisUnitType
            weaponsList, lightChassis, mediumChassis, heavyChassis;

    @Override
    public void load() {
        setupID();

        /*todo: make a better weapon entity system
        currently, to make saving weapons easy, all weapons are assigned to a unit and retrieved later to make a crude ID system
        it's a really stupid solution, but I have nothing better and can't be bothered to overwrite the weapon saving system right now*/
        EntityMapping.nameMap.put("weapons-list", ChassisUnitEntity::new);
        weaponsList = new ChassisUnitType("weapons-list") {
            {
                //filler stats taken from gamma; stats don't matter here, but we need a functional unit for it to work
                defaultController = BuilderAI::new;
                constructor = ChassisUnitEntity::new;
                isCounted = false;

                //variables for customizing where the weapons should be
                float secondaryslot1y = 4.5f;
                float secondaryslot2y = -0.5f;
                float primaryslot1y = -5.5f;
                float primaryslot2y = 12;
                float primaryslot3y = -11.5f;

                //weapons are loaded here and retrieved later
                //normally I'd use a for loop for these but since the locations of the weapons aren't linearly scaled I'll have to stick with this nonsense until I figure out a smarter way to do it
                weapons.addAll(new Weapon("unit-construct-salvo-mount") {{
                                   reload = 31f;
                                   cooldownTime = 90f;
                                   x = 0;
                                   y = secondaryslot1y;
                                   rotateSpeed = 4f;
                                   rotate = true;
                                   shootY = 7f;
                                   shake = 2f;
                                   recoil = 1.5f;
                                   shadow = 12f;
                                   ejectEffect = Fx.casing3;
                                   shootSound = Sounds.shootBig;
                                   mirror = false;

                                   shots = 4;
                                   shotDelay = 3f;
                                   inaccuracy = 1f;
                                   bullet = new BasicBulletType(4f, 18){{
                                       width = 10f;
                                       height = 13f;
                                       shootEffect = Fx.shootBig;
                                       smokeEffect = Fx.shootBigSmoke;
                                       ammoMultiplier = 4;
                                       lifetime = 60f;
                                   }};
                               }},

                        new Weapon("unit-construct-salvo-mount") {{
                            reload = 31f;
                            cooldownTime = 90f;
                            x = 0;
                            y = secondaryslot2y;
                            rotateSpeed = 4f;
                            rotate = true;
                            shootY = 7f;
                            shake = 2f;
                            recoil = 1.5f;
                            shadow = 12f;
                            ejectEffect = Fx.casing3;
                            shootSound = Sounds.shootBig;
                            mirror = false;

                            shots = 4;
                            shotDelay = 3f;
                            inaccuracy = 1f;
                            bullet = new BasicBulletType(4f, 18){{
                                width = 10f;
                                height = 13f;
                                shootEffect = Fx.shootBig;
                                smokeEffect = Fx.shootBigSmoke;
                                ammoMultiplier = 4;
                                lifetime = 60f;
                            }};
                        }},

                        new Weapon("unit-construct-artillery-mount"){{
                            reload = 65f;
                            mirror = false;
                            x = 0f;
                            y = primaryslot1y;
                            rotateSpeed = 1.7f;
                            rotate = true;
                            shootY = 7f;
                            shake = 5f;
                            recoil = 4f;
                            shadow = 12f;

                            shots = 1;
                            inaccuracy = 3f;
                            ejectEffect = Fx.casing3;
                            shootSound = Sounds.artillery;

                            bullet = new ArtilleryBulletType(3.2f, 12){{
                                trailMult = 0.8f;
                                hitEffect = Fx.massiveExplosion;
                                knockback = 1.5f;
                                lifetime = 100f;
                                height = 15.5f;
                                width = 15f;
                                collidesTiles = false;
                                ammoMultiplier = 4f;
                                splashDamageRadius = 40f;
                                splashDamage = 80f;
                                backColor = Pal.missileYellowBack;
                                frontColor = Pal.missileYellow;
                                trailEffect = Fx.artilleryTrail;
                                trailSize = 6f;
                                hitShake = 4f;

                                shootEffect = Fx.shootBig2;

                                status = StatusEffects.blasted;
                                statusDuration = 60f;
                            }};
                        }},
                        new Weapon("unit-construct-artillery-mount"){{
                            reload = 65f;
                            mirror = false;
                            x = 0f;
                            y = primaryslot2y;
                            rotateSpeed = 1.7f;
                            rotate = true;
                            shootY = 7f;
                            shake = 5f;
                            recoil = 4f;
                            shadow = 12f;

                            shots = 1;
                            inaccuracy = 3f;
                            ejectEffect = Fx.casing3;
                            shootSound = Sounds.artillery;

                            bullet = new ArtilleryBulletType(3.2f, 12){{
                                trailMult = 0.8f;
                                hitEffect = Fx.massiveExplosion;
                                knockback = 1.5f;
                                lifetime = 100f;
                                height = 15.5f;
                                width = 15f;
                                collidesTiles = false;
                                ammoMultiplier = 4f;
                                splashDamageRadius = 40f;
                                splashDamage = 80f;
                                backColor = Pal.missileYellowBack;
                                frontColor = Pal.missileYellow;
                                trailEffect = Fx.artilleryTrail;
                                trailSize = 6f;
                                hitShake = 4f;

                                shootEffect = Fx.shootBig2;

                                status = StatusEffects.blasted;
                                statusDuration = 60f;
                            }};
                        }},

                        new Weapon("unit-construct-artillery-mount"){{
                            reload = 65f;
                            mirror = false;
                            x = 0f;
                            y = primaryslot3y;
                            rotateSpeed = 1.7f;
                            rotate = true;
                            shootY = 7f;
                            shake = 5f;
                            recoil = 4f;
                            shadow = 12f;

                            shots = 1;
                            inaccuracy = 3f;
                            ejectEffect = Fx.casing3;
                            shootSound = Sounds.artillery;

                            bullet = new ArtilleryBulletType(3.2f, 12){{
                                trailMult = 0.8f;
                                hitEffect = Fx.massiveExplosion;
                                knockback = 1.5f;
                                lifetime = 100f;
                                height = 15.5f;
                                width = 15f;
                                collidesTiles = false;
                                ammoMultiplier = 4f;
                                splashDamageRadius = 40f;
                                splashDamage = 80f;
                                backColor = Pal.missileYellowBack;
                                frontColor = Pal.missileYellow;
                                trailEffect = Fx.artilleryTrail;
                                trailSize = 6f;
                                hitShake = 4f;

                                shootEffect = Fx.shootBig2;

                                status = StatusEffects.blasted;
                                statusDuration = 60f;
                            }};
                        }},

                        new Weapon("unit-construct-missile-mount"){{
                            reload = 20f;
                            x = 0f;
                            y = secondaryslot1y;

                            shadow = 6f;

                            rotateSpeed = 4f;
                            rotate = true;
                            shots = 2;
                            shotDelay = 3f;
                            inaccuracy = 5f;
                            velocityRnd = 0.1f;
                            shootSound = Sounds.missile;
                            mirror = false;

                            ejectEffect = Fx.none;
                            bullet = new MissileBulletType(2.7f, 12){{
                                width = 8f;
                                height = 8f;
                                shrinkY = 0f;
                                drag = -0.003f;
                                homingRange = 60f;
                                keepVelocity = false;
                                splashDamageRadius = 25f;
                                splashDamage = 10f;
                                lifetime = 80f;
                                trailColor = Color.gray;
                                backColor = Pal.bulletYellowBack;
                                frontColor = Pal.bulletYellow;
                                hitEffect = Fx.blastExplosion;
                                despawnEffect = Fx.blastExplosion;
                                weaveScale = 8f;
                                weaveMag = 1f;
                            }};
                        }},

                        new Weapon("unit-construct-missile-mount"){{
                            reload = 20f;
                            x = 0f;
                            y = secondaryslot2y;

                            shadow = 6f;

                            rotateSpeed = 4f;
                            rotate = true;
                            shots = 2;
                            shotDelay = 3f;
                            inaccuracy = 5f;
                            velocityRnd = 0.1f;
                            shootSound = Sounds.missile;
                            mirror = false;

                            ejectEffect = Fx.none;
                            bullet = new MissileBulletType(2.7f, 12){{
                                width = 8f;
                                height = 8f;
                                shrinkY = 0f;
                                drag = -0.003f;
                                homingRange = 60f;
                                keepVelocity = false;
                                splashDamageRadius = 25f;
                                splashDamage = 10f;
                                lifetime = 80f;
                                trailColor = Color.gray;
                                backColor = Pal.bulletYellowBack;
                                frontColor = Pal.bulletYellow;
                                hitEffect = Fx.blastExplosion;
                                despawnEffect = Fx.blastExplosion;
                                weaveScale = 8f;
                                weaveMag = 1f;
                            }};
                        }},

                        new Weapon("unit-construct-laser-beam-mount"){{
                            top = true;
                            mirror = false;
                            rotate = true;
                            x = 0;
                            shake = 2f;
                            shootY = 4f;
                            rotateSpeed = 4f;
                            y = primaryslot1y;
                            reload = 55f;
                            recoil = 4f;
                            shootSound = Sounds.laser;

                            bullet = new LaserBulletType(140){{
                                colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                                hitEffect = Fx.hitLancer;
                                hitSize = 4;
                                lifetime = 16f;
                                drawSize = 400f;
                                collidesAir = false;
                                length = 173f;
                                ammoMultiplier = 1f;
                            }};
                        }},

                        new Weapon("unit-construct-laser-beam-mount"){{
                            top = true;
                            mirror = false;
                            rotate = true;
                            x = 0;
                            shake = 2f;
                            shootY = 4f;
                            rotateSpeed = 4f;
                            y = primaryslot2y;
                            reload = 55f;
                            recoil = 4f;
                            shootSound = Sounds.laser;

                            bullet = new LaserBulletType(140){{
                                colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                                hitEffect = Fx.hitLancer;
                                hitSize = 4;
                                lifetime = 16f;
                                drawSize = 400f;
                                collidesAir = false;
                                length = 173f;
                                ammoMultiplier = 1f;
                            }};
                        }},

                        new Weapon("unit-construct-laser-beam-mount"){{
                            top = true;
                            mirror = false;
                            rotate = true;
                            x = 0;
                            shake = 2f;
                            shootY = 4f;
                            y = primaryslot3y;
                            reload = 55f;
                            recoil = 4f;
                            shootSound = Sounds.laser;

                            bullet = new LaserBulletType(140){{
                                colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                                hitEffect = Fx.hitLancer;
                                hitSize = 4;
                                lifetime = 16f;
                                drawSize = 400f;
                                collidesAir = false;
                                length = 173f;
                                ammoMultiplier = 1f;
                            }};
                        }},

                        new Weapon("unit-construct-scatter-mount"){{
                            reload = 18f;
                            x = 0f;
                            y = secondaryslot1y;

                            shadow = 6f;

                            rotateSpeed = 15f;
                            rotate = true;
                            shots = 2;
                            shotDelay = 5f;
                            inaccuracy = 10f;
                            shootSound = Sounds.shootSnap;
                            mirror = false;

                            ejectEffect = Fx.none;
                            bullet = new FlakBulletType(4.2f, 3){{
                                lifetime = 60f;
                                ammoMultiplier = 4f;
                                shootEffect = Fx.shootSmall;
                                width = 6f;
                                height = 8f;
                                hitEffect = Fx.flakExplosion;
                                splashDamage = 27f * 1.5f;
                                splashDamageRadius = 15f;
                            }};
                        }},

                        new Weapon("unit-construct-scatter-mount"){{
                            reload = 18f;
                            x = 0f;
                            y = secondaryslot2y;

                            shadow = 6f;

                            rotateSpeed = 15f;
                            rotate = true;
                            shots = 2;
                            shotDelay = 5f;
                            inaccuracy = 10f;
                            shootSound = Sounds.shootSnap;
                            mirror = false;

                            ejectEffect = Fx.none;
                            bullet = new FlakBulletType(4.2f, 3){{
                                lifetime = 60f;
                                ammoMultiplier = 4f;
                                shootEffect = Fx.shootSmall;
                                width = 6f;
                                height = 8f;
                                hitEffect = Fx.flakExplosion;
                                splashDamage = 27f * 1.5f;
                                splashDamageRadius = 15f;
                            }};
                        }},

                        new Weapon("unit-construct-fuse-mount"){{
                            y = primaryslot1y;
                            x = 0f;
                            shootY = 7f;
                            reload = 35;
                            shake = 4f;
                            rotateSpeed = 2f;
                            ejectEffect = Fx.casing1;
                            shootSound = Sounds.shotgun;
                            rotate = true;
                            shadow = 12f;
                            recoil = 3f;
                            shots = 3;
                            spacing = 20f;
                            mirror = false;

                            bullet = new ShrapnelBulletType(){{
                                length = 90;
                                damage = 105f;
                                toColor = Color.valueOf("b2c6d2");
                                shootEffect = smokeEffect = Fx.sparkShoot;
                            }};
                        }},

                        new Weapon("unit-construct-fuse-mount"){{
                            y = primaryslot2y;
                            x = 0f;
                            shootY = 7f;
                            reload = 35;
                            shake = 4f;
                            rotateSpeed = 2f;
                            ejectEffect = Fx.casing1;
                            shootSound = Sounds.shotgun;
                            rotate = true;
                            shadow = 12f;
                            recoil = 3f;
                            shots = 3;
                            spacing = 20f;
                            mirror = false;

                            bullet = new ShrapnelBulletType(){{
                                length = 90;
                                damage = 105f;
                                toColor = Color.valueOf("b2c6d2");
                                shootEffect = smokeEffect = Fx.sparkShoot;
                            }};
                        }},

                        new Weapon("unit-construct-fuse-mount"){{
                            y = primaryslot3y;
                            x = 0f;
                            shootY = 7f;
                            reload = 35;
                            shake = 4f;
                            rotateSpeed = 2f;
                            ejectEffect = Fx.casing1;
                            shootSound = Sounds.shotgun;
                            rotate = true;
                            shadow = 12f;
                            recoil = 3f;
                            shots = 3;
                            spacing = 20f;
                            mirror = false;

                            bullet = new ShrapnelBulletType(){{
                                length = 90;
                                damage = 105f;
                                toColor = Color.valueOf("b2c6d2");
                                shootEffect = smokeEffect = Fx.sparkShoot;
                            }};
                        }},

                        new Weapon("unit-construct-oil-hose-mount"){{
                            reload = 6f;
                            x = 0f;
                            y = secondaryslot1y;

                            shadow = 6f;

                            rotateSpeed = 6f;
                            rotate = true;
                            shots = 3;
                            shotDelay = 0;
                            inaccuracy = 5f;
                            velocityRnd = 0.1f;
                            shootSound = Sounds.spray;
                            mirror = false;

                            ejectEffect = Fx.none;
                            bullet = new LiquidBulletType(Liquids.oil){{
                                lifetime = 40;
                                speed = 4f;
                                knockback = 0.3f;
                                puddleSize = 8f;
                                orbSize = 4f;
                                drag = 0.001f;
                                statusDuration = 60f * 3f;
                                damage = 0.2f;
                            }};
                        }},

                        new Weapon("unit-construct-oil-hose-mount"){{
                            reload = 6f;
                            x = 0f;
                            y = secondaryslot2y;

                            shadow = 6f;

                            rotateSpeed = 6f;
                            rotate = true;
                            shots = 3;
                            shotDelay = 0;
                            inaccuracy = 5f;
                            velocityRnd = 0.1f;
                            shootSound = Sounds.spray;
                            mirror = false;

                            ejectEffect = Fx.none;
                            bullet = new LiquidBulletType(Liquids.oil){{
                                lifetime = 40;
                                speed = 4f;
                                knockback = 0.3f;
                                puddleSize = 8f;
                                orbSize = 4f;
                                drag = 0.001f;
                                statusDuration = 60f * 3f;
                                damage = 0.2f;
                            }};
                        }}

                        );
            }};

        EntityMapping.nameMap.put("light-unit-chassis", ChassisUnitEntity::new);
        lightChassis = new ChassisUnitType("light-unit-chassis") {{
            rotateShooting = false;
            flying = true;
            hitSize = 8;
            health = 250;
            armor = 0;
            speed = 4f;
            accel = 0.15f;
            drag = 0.045f;
            isCounted = true;
            constructor = ChassisUnitEntity::new;
            defaultController = FlyingAI::new;
            //the placeholder weapons are used to pre-generate slots where the weapons will later go
            for (int i = 0; i < 2; i++) {
                weapons.add(placeholderWeapon);
            }
        }};

        EntityMapping.nameMap.put("medium-unit-chassis", ChassisUnitEntity::new);
        mediumChassis = new ChassisUnitType("medium-unit-chassis") {{
            rotateShooting = false;
            flying = true;
            hitSize = 16;
            health = 750;
            armor = 3;
            speed = 3.5f;
            accel = 0.025f;
            drag = 0.03f;
            isCounted = true;
            constructor = ChassisUnitEntity::new;
            defaultController = FlyingAI::new;
            engineOffset = 8f;
            engineSize = 6f;
            for (int i = 0; i < 3; i++) {
                weapons.add(placeholderWeapon);
            }
        }};

        EntityMapping.nameMap.put("heavy-unit-chassis", ChassisUnitEntity::new);
        heavyChassis = new ChassisUnitType("heavy-unit-chassis") {{
            rotateShooting = false;
            flying = true;
            hitSize = 32;
            health = 5000;
            armor = 7;
            speed = 1.5f;
            accel = 0.02f;
            drag = 0.005f;
            isCounted = true;
            constructor = ChassisUnitEntity::new;
            defaultController = FlyingAI::new;
            for (int i = 0; i < 5; i++) {
                weapons.add(placeholderWeapon);
            }
        }};
    }
}
