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
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CausticWater;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.SkeletonKey;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GooBlob;
import com.shatteredpixel.shatteredpixeldungeon.levels.TownLevel;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FinalGooSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GooSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class FinalGoo extends Mob {

	private static final int BIG_GOO = 100;
	private static final int SMALL_GOO = 30;

	private static final int MAX_DMG = 10;
	private static final int MAX_PUMPED = 45;
	private static final int REGEN = 1;

	private int pumpedUp = 0;
	private int pumpedCooldown = 0;

	{
		HP = HT = 300;
		EXP = 50;
		spriteClass = FinalGooSprite.class;
		HUNTING = new FinalGooHunting();

		properties.add(Property.BOSS);
		properties.add(Property.DEMONIC);
		properties.add(Property.ACIDIC);
		properties.add(Property.LARGE);
	}

	private boolean bigGoo() {
		return HP >= BIG_GOO;
	}

	private boolean smallGoo() {
		return HP <= SMALL_GOO;
	}

	private boolean mediumGoo() {
		return !bigGoo() && !smallGoo();
	}

	public void updateGooSize() {
		if(bigGoo()) {
			baseSpeed = 1f;
			properties.add(Property.LARGE);
			if(sprite != null)
				((FinalGooSprite)sprite).setGooSize(FinalGooSprite.GooSize.BIG);
			return;
		}
		if(mediumGoo()) {
			baseSpeed = 1f;
			properties.remove(Property.LARGE);
			if(sprite != null)
				((FinalGooSprite)sprite).setGooSize(FinalGooSprite.GooSize.MEDIUM);
			return;
		}
		if(smallGoo()) {
			baseSpeed = 2f;
			properties.remove(Property.LARGE);
			if(sprite != null)
				((FinalGooSprite)sprite).setGooSize(FinalGooSprite.GooSize.SMALL);
			return;
		}
	}

	@Override
	public int damageRoll() {
		int damage = Random.NormalIntRange(12, 25);
		if(bigGoo())
			return Random.NormalIntRange(20, 45);
		if(smallGoo())
			return Random.NormalIntRange(4, 10); //8-20 per turn (2x speed)
		return damage;
	}

	@Override
	protected float attackDelay() {
		if(smallGoo())
			return super.attackDelay() * 0.5f;
		return super.attackDelay();
	}

	@Override
	public int attackSkill( Char target ) {return 30;}

	@Override
	public int defenseSkill(Char enemy) {
		if(bigGoo())
			return 10;
		if(smallGoo())
			return 30;
		return 20;
	}

	private static class SplitDmgSource{};
    private static class BigGooEarthDmgSource{}; //damage sources for goo

	private void splitGoo(int hp, int pos) {
		FinalGoo clone = new FinalGoo();
		clone.HP = hp;
		clone.EXP = 0;
		if (buff( Burning.class ) != null) {
			Buff.affect( clone, Burning.class ).reignite( clone );
		}
		if (buff( Poison.class ) != null) {
			Buff.affect( clone, Poison.class ).set(2);
		}
		if (buff(Doom.class ) != null) {
			Buff.affect( clone, Doom.class);
		}
		clone.pos = pos;
		clone.state = clone.HUNTING;
		Dungeon.level.occupyCell(clone);
		GameScene.add(clone);
		Actor.addDelayed( new Pushing( clone, this.pos, clone.pos ), -1 );
		clone.updateGooSize();
	}

	private int trueDamage(int damage) {
		if (damage > MAX_DMG) {
			damage = MAX_DMG + (int)(Math.sqrt(8*(damage - MAX_DMG) + 1) - 1)/2;
		}
		return damage;
	}

	@Override
	public int defenseProc( Char enemy, int damage ) {
		return super.defenseProc(enemy, damage);
	}

	@Override
	public boolean act() {
		if(pumpedCooldown > 0)
			--pumpedCooldown;
		if(pumpedUp > 0)
			--pumpedUp;
		if (Dungeon.level.water[pos] && HP < HT && !bigGoo()) {
			/*if (HP*2 == HT) {
				BossHealthBar.bleed(false);
				((GooSprite)sprite).spray(false);
			}*/
			heal();
		}
		Blob causticWater=Dungeon.level.blobs.get(CausticWater.class);
		if(causticWater != null && causticWater.cur != null && causticWater.cur[pos] > 0) {
			heal();
		}
		if(Dungeon.level.water[pos] && bigGoo()) {
			GameScene.add(Blob.seed(pos,100, CausticWater.class));
		}
		if(!Dungeon.level.water[pos] && bigGoo()) {
			Dungeon.level.setCellToWater(false, pos);
			damage(REGEN, new BigGooEarthDmgSource());//this has damage of around 1, it shouldn't trigger slime or fly logic, but it will display damage number
		}
		updateGooSize();
		if (state != SLEEPING){
			Dungeon.level.seal();
		}
		return super.act();
	}

	public void heal() {
		sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
		HP += REGEN;
	}

	private boolean tryToShoot(Char enemy) {
		ArrayList<Integer> candidates = new ArrayList<>();
		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; ++i) {
			int curPos = enemy.pos + PathFinder.NEIGHBOURS8[i];
			Ballistica trajectory = new Ballistica(pos, curPos, Ballistica.PROJECTILE);
			if (trajectory.collisionPos == curPos && Actor.findChar( curPos ) == null && !Dungeon.level.solid[curPos])
				candidates.add(curPos);
		}
		if(candidates.size() > 0) {
			splitGoo(MAX_DMG, Random.element(candidates));
			pumpedUp = 0;
			pumpedCooldown = 10;
			spend(TICK);
			return true;
		}
		return false;
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int( 5 ) == 0) {
			Buff.affect( enemy, Ooze.class ).set( Ooze.DURATION );
			enemy.sprite.burst( 0x000000, 5 );
		}
		return damage;
	}

	@Override
	public void updateSpriteState() {
		super.updateSpriteState();
	}

	@Override
	protected boolean getCloser( int target ) {
		return super.getCloser( target );
	}

	@Override
	public void damage(int dmg, Object src) {
		dmg = trueDamage(dmg);
		if(!smallGoo() && (HP - dmg) / 2 > MAX_DMG && dmg >= MAX_DMG) {
			ArrayList<Integer> candidates = new ArrayList<>();
			boolean[] solid = Dungeon.level.solid;

			int[] neighbours = {pos + 1, pos - 1, pos + Dungeon.level.width(), pos - Dungeon.level.width()};
			for (int n : neighbours) {
				if (!solid[n] && Actor.findChar( n ) == null) {
					candidates.add( n );
				}
			}
			if (candidates.size() > 0) {
				int cloneHp = Random.Int(MAX_DMG, (HP - dmg) / 2);
				splitGoo(cloneHp, Random.element(candidates));
				HP -= cloneHp - MAX_DMG;
			}
		}
		/*if (!BossHealthBar.isAssigned()){
			BossHealthBar.assignBoss( this );
		}
		boolean bleeding = (HP*2 <= HT);*/
		/*if (dmg > MAX_DMG) {
			dmg = MAX_DMG + (int)(Math.sqrt(8*(dmg - MAX_DMG) + 1) - 1)/2;
		}*/
		super.damage(dmg, src);
		updateGooSize();
		/*if ((HP*2 <= HT) && !bleeding){
			BossHealthBar.bleed(true);
			sprite.showStatus(CharSprite.NEGATIVE, Messages.get(this, "enraged"));
			((GooSprite)sprite).spray(true);
			yell(Messages.get(this, "gluuurp"));
		}*/
		LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
		if (lock != null) lock.addTime(dmg);
	}

	@Override
	public void die( Object cause ) {
		Dungeon.level.setCellToWater(false, pos);
		GameScene.add(Blob.seed(pos,20, CausticWater.class));

		super.die( cause );
		boolean lastGoo = true;
		for(Mob mob: Dungeon.level.mobs) {
			if(mob instanceof FinalGoo && mob.isAlive()) {
				lastGoo = false;
				break;
			}
		}
		if (lastGoo) {
			Dungeon.level.unseal();
			GameScene.bossSlain();
			TownLevel.Progression.gooSlain = true;
			TownLevel.Progression.updateStage();
		}
		/*Dungeon.level.unseal();
		
		GameScene.bossSlain();
		
		Badges.validateBossSlain();
		
		yell( Messages.get(this, "defeated") );*/
	}
	
	@Override
	public void notice() {
		super.notice();
		/*if (!BossHealthBar.isAssigned()) {
			BossHealthBar.assignBoss(this);
			yell(Messages.get(this, "notice"));
		}*/
	}

	private static final String PUMPEDUP = "pumpedup";
	private static final String PUMPEDCOOLDOWN = "pumpedcooldown";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put(PUMPEDUP, pumpedUp);
		bundle.put(PUMPEDCOOLDOWN,pumpedCooldown);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {

		super.restoreFromBundle( bundle );
		pumpedUp = bundle.getInt( PUMPEDUP );
		pumpedCooldown = bundle.getInt(PUMPEDCOOLDOWN);
		updateGooSize();
		/*if (state != SLEEPING) BossHealthBar.assignBoss(this);
		if ((HP*2 <= HT)) BossHealthBar.bleed(true);*/

	}

	@Override
	public String description() {
		if(bigGoo())
			return Messages.get(this, "desc") + "\n\n" + Messages.get(this, "big_desc");
		if(smallGoo())
			return Messages.get(this, "desc") + "\n\n" + Messages.get(this, "small_desc");
		return Messages.get(this, "desc") + "\n\n" + Messages.get(this, "medium_desc");
	}

	private void pumpUp() {
		if(bigGoo() && pumpedUp < MAX_PUMPED) {
			GLog.n(Messages.get(Goo.class, "pumpup"));
			sprite.showStatus(CharSprite.NEGATIVE,"!!!");
			pumpedUp += 10;
		}
		spend(TICK);
	}

	private class FinalGooHunting extends Hunting {
		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			if (!enemyInFOV || canAttack(enemy) || !bigGoo()) {
				return super.act(enemyInFOV, justAlerted);
			} else {
				enemySeen = true;
				target = enemy.pos;

				int oldPos = pos;
				if (pumpedUp > 0 && pumpedCooldown == 0) {
					if (tryToShoot(enemy)) {
						return true;
					}
					if (pumpedUp < MAX_PUMPED) {
						pumpUp();
						return true;
					}
				}
				if (pumpedUp == 0 && Random.Int(100 / distance(enemy)) == 0) {
					pumpUp();
					return true;
				} else if (getCloser( target )) {
					spend( 1 / speed() );
					return moveSprite( oldPos,  pos );
				} else if (pumpedUp == 0) {
					pumpUp();
					return true;
				}
				return super.act(enemyInFOV, justAlerted);
			}
		}
	}
}
