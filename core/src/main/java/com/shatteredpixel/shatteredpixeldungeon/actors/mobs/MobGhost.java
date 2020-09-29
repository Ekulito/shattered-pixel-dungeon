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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MobGhostSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.WarlockSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.WraithSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MobGhost extends Mob implements Callback {
	//ytadpailipmexom
	private static final float BLINK_CHANCE = 0.125f;
	private static final float TIME_TO_ZAP	= 1f;
	
	{
		spriteClass = MobGhostSprite.class;
		
		HP = HT = 50;
		defenseSkill=attackSkill(null) * 5/2;// normal wraiths have atk x5

		EXP = 5;

		state = WANDERING;

		flying = true;

		properties.add(Property.UNDEAD);
		properties.add(Property.MINIBOSS);
	}

	@Override
	public int attackSkill( Char target ) {
		return 10 + 6;
	}//equal to lvl 6 wraith

	@Override
	protected boolean canAttack( Char enemy ) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	private void blink() {
		ArrayList<Integer> cells = new ArrayList<>();

		for (int cell = 0; cell < Dungeon.level.length(); ++cell) {
			if(Dungeon.level.passable[cell] && pos != cell && fieldOfView[cell] ) {
				cells.add( cell );
			}
		}

		int newPos = !cells.isEmpty() ? Random.element( cells ) : pos ;

		if (Dungeon.level.heroFOV[pos]) {
			CellEmitter.get(pos).start( ShadowParticle.UP, 0.01f, Random.IntRange(5, 10) );
		}

		if (Dungeon.level.heroFOV[newPos]) {
			CellEmitter.get(newPos).start(ShadowParticle.MISSILE, 0.01f, Random.IntRange(5, 10));
		}

		((MobGhostSprite)sprite).blink(pos, newPos);

		move( newPos );

		spend( 1 / speed() );
	}

	@Override
	protected boolean doAttack( Char enemy ) {

		if (Dungeon.level.adjacent( pos, enemy.pos )) {
			if (Random.Float() < BLINK_CHANCE) {
				blink();
				return true;
			}
			return super.doAttack( enemy );
		} else {

			if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
				sprite.zap( enemy.pos );
				return false;
			} else {
				zap();
				return true;
			}
		}
	}

	public static class GhostBolt{}

	private void zap() {
		spend( TIME_TO_ZAP );

		if (hit( this, enemy, true )) {

			int dmg = damageRoll();
			enemy.damage( dmg, new MobGhost.GhostBolt() );

			if (enemy == Dungeon.hero && !enemy.isAlive()) {
				Dungeon.fail( getClass() );
				GLog.n( Messages.get(this, "bolt_kill") );
			}
		} else {
			enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
		}
	}

	public void onZapComplete() {
		zap();
		next();
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		if (distance(enemy) <= 1 && isAlive()) {
			int healed=damage / 2;
			if (healed > 0) {
				HP = Math.min(HT, HP + healed);
				if(sprite.visible){
					sprite.emitter().burst(Speck.factory(Speck.HEALING),1);
				}
			}
		}
		return damage;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1 + 6/2, 2 + 6 );
	}//equal to lvl 6 wraith

	@Override
	public void call() {
		next();
	}

	@Override
	public void die( Object cause ) {
		int tempPos = pos;//because we die and our position becomes nowhere
		yell(Messages.get(this,"ondefeat"));
		super.die( cause );
		Dungeon.level.drop(Ghost.Quest.weapon.identify(), tempPos);
		Dungeon.level.drop(Ghost.Quest.armor.identify(), tempPos);
		Ghost.Quest.fail();
	}
}
