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

package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.FinalGoo;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Otiluke;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.portalitems.GooPortal;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Bundle;

public class TownLevel extends Level {
	int stage = 0;
	private static final Class<? extends Item>[] itemTypes = new Class[]
			{
					//ScrollOfMagicMapping.class, ScrollOfIdentify.class, ScrollOfRemoveCurse.class, ScrollOfTeleportation.class,
					//PotionOfLevitation.class, PotionOfLiquidFlame.class, PotionOfMindVision.class, PotionOfPurity.class,
					//PotionOfParalyticGas.class, PotionOfToxicGas.class, PotionOfHaste.class
			};
	private static final int[] itemSpots = new int[]
			{
					48*7+7, 48*7+8, 48*7+9, 48*7+10,
					48*8+7, 48*8+8, 48*8+9, 48*8+10,
					48*9+7, 48*9+8, 48*9+9
			};
	//hi dachhack
	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
	}

	@Override
	public int effectiveDepth() { return 1; }

	@Override
	public boolean isFlat() {
		return true;
	}

	@Override
	public String tilesTex() {
		return Assets.Environment.TILES_TOWN;
	}
	
	@Override
	public String waterTex() {
		return Assets.Environment.WATER_SEWERS;
	}
	
	@Override
	protected boolean build() {

		setSize(48, 48);
		map = TownLayouts.TOWN_LAYOUT.clone();

		buildFlagMaps();
		cleanWalls();

		entrance = 25 + width * 21;
		exit = 5 + width * 40;
		
		return true;
	}

	
	@Override
	protected void createMobs() {
		Mob shopkeeper = new Shopkeeper();
		shopkeeper.pos = 13 + width*10;
		mobs.add(shopkeeper);

		Mob otiluke = new Otiluke();
		otiluke.pos = entrance + 1;
		mobs.add(otiluke);
	}

	@Override
	protected void createItems() {
		for(int i = 0; i < itemTypes.length; ++i) {
			try {
				this.drop(itemTypes[i].newInstance().identify(),itemSpots[i]).type = Heap.Type.FOR_SALE_RESTOCKABLE;
			} catch (InstantiationException ignored) {
			} catch (IllegalAccessException ignored) {
			}
		}
		this.drop(new GooPortal(),20 + width * 6);
	}
	
	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.HIGH_GRASS:
				return Messages.get(this, "high_grass_name");
			case Terrain.STATUE:
				return Messages.get(this, "pedestal_name");
			case Terrain.BARRICADE:
				return Messages.get(this, "wall_name");
			default:
				return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(this, "empty_deco_desc");
			case Terrain.STATUE:
			case Terrain.BARRICADE:
				return "";
			default:
				return super.tileDesc( tile );
		}
	}
	
	@Override
	public int randomRespawnCell(Char c){
		return -1;
	}

	public void updateStage() {
		while (stage < Progression.stage) {
			++stage;
			if (stage == 1) {
				Char ch = Actor.findChar(entrance + 1);
				if(ch instanceof Otiluke) {
					ch.sprite.killAndErase();
					ch.destroy();
				}
				Mob newOtiluke = new Otiluke();
				newOtiluke.pos = 21 + width * 11;
				GameScene.add(newOtiluke);
				int otilukesDoor=22+width*12;
				set(otilukesDoor, Terrain.DOOR);
				GameScene.updateMap(otilukesDoor);
			}
		}
	}

	private static final String STAGE = "stage";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(STAGE, stage);
	}
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		if(bundle.contains(STAGE))
			stage = bundle.getInt(STAGE);
		else stage = 0;
		updateStage();
	}

	public static class Progression {
		public static int stage;
		public static boolean gooSlain;

		private static final String STAGE = "stage";
		private static final String GOO_SLAIN = "gooslain";
		private static final String NODE = "dh-progression";
		public static void updateStage() {
			return;
		}
		public static void reset() {
			stage = 0;
			gooSlain = false;
		}
		public static void storeInBundle( Bundle bundle ) {
			Bundle node = new Bundle();
			node.put(STAGE, stage);
			node.put(GOO_SLAIN, gooSlain);
			bundle.put(NODE, node);
		}
		public static void restoreFromBundle( Bundle bundle ) {

			Bundle node = bundle.getBundle(NODE);
			reset();

			if (!node.isNull()) {
				if(node.contains(STAGE))
					stage = node.getInt(STAGE);
				if(node.contains(GOO_SLAIN))
					gooSlain = node.getBoolean(GOO_SLAIN);
			}
		}
	}
}
