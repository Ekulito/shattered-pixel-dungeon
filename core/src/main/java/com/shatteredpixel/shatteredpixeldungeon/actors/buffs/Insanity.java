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

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Glaive;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static java.lang.Integer.min;

public class Insanity extends Buff implements Hero.Doom {

	private static final int MAXINSANITY=30;
	private static final int INSANITYDECREASE=1;

	private int stress=0;

	private static final String STRESS = "stress";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( STRESS, stress );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		stress = bundle.getInt( STRESS );
	}

	@Override
	public boolean act() {

		if (target.isAlive() && target instanceof Hero) {

			Hero hero = (Hero)target;

			if(hero.visibleEnemies()!=0) {
				stress+=hero.visibleEnemies();
				if(stress>=MAXINSANITY) {
					doInsaneThing(hero);
					stress=0;
					spend(TICK*10f);
				}
				else spend( TICK );
			} else {
				stress-=INSANITYDECREASE;
				if(stress<0)
					stress=0;
				spend( TICK );
			}

		} else {

			diactivate();

		}

		return true;
	}

	private void doInsaneThing(Hero hero) {
		int effect= Random.IntRange(1,3);
		switch (effect) {
			case 1:
				Buff.affect(hero,Blindness.class,5);
				GLog.n(Messages.get(this,"effect1"));
				break;
			case 2:
				for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
					mob.beckon(hero.pos);
				}
				hero.sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
				GLog.n(Messages.get(this,"effect2"));
				break;
			case 3:
				int damage = Math.max( 0,  (2 + Dungeon.effectiveDepth()/2) - hero.drRoll()/2 );
				Buff.affect( hero, Bleeding.class ).set( damage );
				GLog.n(Messages.get(this,"effect3"));
				break;
		}
	}

	@Override
	public int icon() {
		if (stress == 0) {
			return BuffIndicator.WELL_FED;
		} else if (stress < MAXINSANITY - 5) {
			return BuffIndicator.HUNGER;
		} else {
			return BuffIndicator.STARVATION;
		}
	}

	@Override
	public String toString() {
		String result;
		if (stress == 0) {
			result = Messages.get(this, "calm");
		} else if(stress < MAXINSANITY - 5){
			result = Messages.get(this, "worried");
		}
		else result = Messages.get(this,"insane");

		return result;
	}

	@Override
	public String desc() {
		String result;
		if (stress == 0) {
			result = Messages.get(this, "calm_desc");
		} else if(stress < MAXINSANITY - 5){
			result = Messages.get(this, "worried_desc");
		}
		else result = Messages.get(this,"insane_desc");

		//result += Messages.get(this, "desc");

		return result;
	}

	@Override
	public void onDeath() {

		Badges.validateDeathFromHunger();

		Dungeon.fail( getClass() );
		GLog.n( Messages.get(this, "ondeath") );
	}
}
