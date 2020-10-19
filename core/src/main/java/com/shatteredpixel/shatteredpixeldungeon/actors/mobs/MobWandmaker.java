/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorrosion;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MobGhostSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MobWandmakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//currently broken wands:
//living earth
//warding
//blastwave
public class MobWandmaker extends Mob implements Callback {
    private static final float TIME_TO_ZAP	    = 1f;
    private static final float TIME_TO_RECHARGE = 1f;
    private static final float BLINK_CHANCE     = 1f / 3f;

    private int charges;
    {
        spriteClass = MobWandmakerSprite.class;
        HP = HT = 75;
        defenseSkill = 8;

        EXP = 15;

        WANDERING = new WandmakerWandering();
        state = WANDERING;
        charges = Wandmaker.Quest.wand1.maxCharges + Wandmaker.Quest.wand2.maxCharges;

        properties.add(Property.MINIBOSS);
    }

    @Override
    public int attackSkill( Char target ) {
        return 8;
    }

    protected boolean canAttack1(Char enemy){
        if(charges < Wandmaker.Quest.wand1.chargesPerCast())
            return false;
        return new Ballistica( pos, enemy.pos, Wandmaker.Quest.wand1.collisionProperties(enemy.pos)|1).collisionPos == enemy.pos;
    }

    protected boolean canAttack2(Char enemy){
        if(charges < Wandmaker.Quest.wand2.chargesPerCast())
            return false;
        return new Ballistica( pos, enemy.pos, Wandmaker.Quest.wand2.collisionProperties(enemy.pos)|1).collisionPos == enemy.pos;
    }

    protected boolean canRecharge() {
        return (charges < Wandmaker.Quest.wand1.maxCharges + Wandmaker.Quest.wand2.maxCharges);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return canAttack1(enemy) || canAttack2(enemy) || canRecharge();
        //that |1 makes it stop at target position, no matter what
    }

    protected int maxBlink(Char enemy){
        int result = 0;
        for (int cell = 0; cell < Dungeon.level.length(); ++cell) {
            if(Dungeon.level.passable[cell] && pos != cell && fieldOfView[cell] && !Dungeon.level.pit[cell]) {
                result = Math.max(result,Dungeon.level.distance(enemy.pos,cell));
            }
        }
        if(result > 6)
            result = 6;
        return result;
    }

    protected void blink(Char enemy){
        --charges;
        int dist=maxBlink(enemy);

        ArrayList<Integer> cells = new ArrayList<>();

        for (int cell = 0; cell < Dungeon.level.length(); ++cell) {
            if(Dungeon.level.passable[cell] && pos != cell && fieldOfView[cell] && Dungeon.level.distance(enemy.pos, cell) >= dist && !Dungeon.level.pit[cell]) {
                cells.add( cell );
            }
        }
        int newPos = !cells.isEmpty() ? Random.element( cells ) : pos ;

        ((MobWandmakerSprite)sprite).blink(pos, newPos);
        move( newPos );

        spend(TIME_TO_ZAP);
    }

    @Override
    protected boolean doAttack( Char enemy ){
        if(charges == 0) {
            recharge();
            return true;
        }
        if(Dungeon.level.adjacent(pos, enemy.pos) && maxBlink(enemy) > 2 && Random.Float() < BLINK_CHANCE) {
            blink(enemy);
            return true;
        }
        boolean can1=canAttack1(enemy) && (charges >= Wandmaker.Quest.wand1.chargesPerCast());
        boolean can2=canAttack2(enemy) && (charges >= Wandmaker.Quest.wand2.chargesPerCast());
        if(!can1&&!can2) {
            recharge();
            return true;
        }
        Wand wandToUse;
        if(can1 && !can2)
            wandToUse = Wandmaker.Quest.wand1;
        else if(can2 && !can1)
            wandToUse = Wandmaker.Quest.wand2;
        else if(Random.IntRange(1,2) == 1)
            wandToUse = Wandmaker.Quest.wand1;
        else wandToUse = Wandmaker.Quest.wand2;

        charges-=wandToUse.chargesPerCast();
        wandToUse.curCharges = wandToUse.maxCharges;
        wandToUse.userAsChar = this;
        wandToUse.shootAt(enemy.pos);
        wandToUse.curCharges = wandToUse.maxCharges;
        spend(TIME_TO_ZAP);
        return true;
    }

    protected boolean recharge() {
        if(charges < Wandmaker.Quest.wand1.maxCharges + Wandmaker.Quest.wand2.maxCharges) {
            ++charges;
            if(Dungeon.hero.fieldOfView[pos]) {
                sprite.showStatus(CharSprite.NEUTRAL,Messages.get(this,"rc_word"));
            }
            spend(TIME_TO_RECHARGE);
            return true;
        }
        return false;
    }

    @Override
    public int damageRoll() { return Random.NormalIntRange( 1, 4 ); }

    private static final String CHARGES = "charges";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put(CHARGES, charges);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        charges = bundle.getInt( CHARGES );
    }

    @Override
    public void call() {
        next();
    }

    @Override
    public void die( Object cause ) {
        int tempPos = pos;
        super.die( cause );
        Dungeon.level.drop(Wandmaker.Quest.wand1.identify(), tempPos).sprite.drop();
        Dungeon.level.drop(Wandmaker.Quest.wand2.identify(), tempPos).sprite.drop();
        Wandmaker.Quest.complete();
    }

    public class WandmakerWandering extends Wandering {
        @Override
        protected boolean continueWandering(){
            if(canRecharge())
                return recharge();
            return super.continueWandering();
        }
    }
}
