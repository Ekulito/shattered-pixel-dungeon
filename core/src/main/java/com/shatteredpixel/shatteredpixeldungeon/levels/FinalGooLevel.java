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
import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.FinalGoo;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.levels.builders.Builder;
import com.shatteredpixel.shatteredpixeldungeon.levels.builders.FigureEightBuilder;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.RegularPainter;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.SewerPainter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.secret.RatKingRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.sewerboss.GooBossRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.sewerboss.SewerBossEntranceRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.sewerboss.SewerBossExitRoom;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.StandardRoom;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.Group;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class FinalGooLevel extends SewerLevel {
	private static final int CX1 = 3;
	private static final int CY1 = 3;
	private static final int CX2 = 3;
	private static final int CY2 = 3;

	@Override
	public int effectiveDepth() {return 25;}

	@Override
	protected boolean build() {

		setSize(43, 43);//39x39
		rooms = new ArrayList<>();
		createMap();

		buildFlagMaps();
		cleanWalls();

		entrance = width*(height - 1 - CY2) + height / 2;
		exit = 0;

		return true;
	}

	private void createMap() {
		for(int i = 0; i < width; ++i) {
			for(int q = 0; q < height; ++q) {
				int pos = i + q*width;
				if(i < CX1 || i + CX2 >= width || q < CY1 || q + CY2 >= height)//i=cx1, i= width-cx2-1
					map[pos] = Terrain.WALL;
				else map[pos] = Terrain.EMPTY;
			}
		}
		map[CX1 + CY1*width] = map[CX1 + (height - CY2 - 1)*width] = map[width - CX2 - 1 + CY1*width] = map[width - CX2 - 1 + (height - CY2 - 1)*width] = Terrain.WALL;
		//set pillars
		int widthX = Random.IntRange(3,4) * 3 - 1;
		int widthY = Random.IntRange(3,4) * 3 - 1;
		int gapX = Random.IntRange(3, 5);
		int gapY = Random.IntRange(3, 5);
		setPillar(CX1 + gapX, CX1 + gapX + widthX, CY1 + gapY, CY1 + gapY + widthY);
		setPillar(width - CX2 - gapX - widthX, width - CX2 - gapX, CY1 + gapY, CY1 + gapY + widthY);
		setPillar(CX1 + gapX, CX1 + gapX + widthX, height - CY2 - gapY - widthY, height - CY2 - gapY);
		setPillar(width - CX2 - gapX - widthX, width - CX2 - gapX, height - CY2 - gapY - widthY, height - CY2 - gapY);
		new SewerPainter().setWater(0.3f, 5).paintWater(this, rooms);
		for (int i=0; i < width; ++i) {
			int q = height / 2;
			int pos = i+q*width;
			if(i == 0 || i == width - 1) {
				continue;
			}
			if (i == 1 || i == width - 2) {
				map[pos] = Terrain.EMPTY;
				continue;
			}
			if (i == 2 || i == width - 3) {
				map[pos] = Terrain.LOCKED_DOOR;
				continue;
			}
			if(i == 3 || i == width - 4) {
				map[pos - width*2] = Terrain.STATUE;
				map[pos + width*2] = Terrain.STATUE;
			}
			if(map[pos] != Terrain.WATER)
				map[pos] = Terrain.EMPTY_SP;
		}
		for (int q = 0; q < height; ++q) {
			int i = width / 2;
			int pos = i+q*width;
			if (q == 0 || q == height - 1) {
				continue;
			}
			if (q == 1 || q == height - 2) {
				map[pos] = Terrain.EMPTY;
				continue;
			}
			if (q == 2 || q == height - 3) {
				map[pos] = Terrain.LOCKED_DOOR;
				continue;
			}
			if (q == 3 || q == height - 4) {
				map[pos - 2] = Terrain.STATUE;
				map[pos + 2] = Terrain.STATUE;
			}
			if(map[pos] != Terrain.WATER)
				map[pos] = Terrain.EMPTY_SP;
		}
		int center=width/2 + width*(height/2);
		for(int i = 0; i < PathFinder.NEIGHBOURS9.length; ++i) {
			map[center + PathFinder.NEIGHBOURS9[i]] = Terrain.WATER;
		}
		for(int i = width / 2 - 2; i <= width / 2 + 2; ++i) {
			for(int q = height / 2 - 2; q <= height / 2 + 2; ++q) {
				if (map[i+q*width] != Terrain.WATER)
					map[i+q*width] = Terrain.EMPTY_SP;
			}
		}
	}

	private void setPillar(int l, int r, int u, int d) { //walls at [l,r) [u,d)
		--l;
		--u;
		for(int i = l; i <= r; ++i) {
			for(int q = u; q <= d; ++q) {
				if ((i-l) % 3 == 0 || (q-u) % 3 == 0)
					map[i+q*width] = Terrain.WATER;
				else map[i+q*width] = Terrain.WALL;
			}
		}
	}

	@Override
	protected void createMobs() {
		Mob goo = new FinalGoo();
		goo.pos = width/2 + width*(height/2);
		mobs.add(goo);

		Mob smallGoo1=new FinalGoo();
		smallGoo1.HP = smallGoo1.HT / 2;
		smallGoo1.pos = goo.pos + width - 1;
		mobs.add(smallGoo1);

		Mob smallGoo2=new FinalGoo();
		smallGoo2.HP = smallGoo2.HT / 2;
		smallGoo2.pos = goo.pos + width + 1;
		mobs.add(smallGoo2);
	}

	@Override
	protected void createItems() {

	}

	@Override
	public int randomRespawnCell( Char ch ) {
		for(int i = 0; i < 40; ++i) {
			int cell = width*(height - 1 - CY2 - Random.IntRange(0, 3)) + height / 2 + Random.IntRange(-3, 3);
			if(Actor.findChar( cell ) == null && passable[cell] && (!Char.hasProp(ch, Char.Property.LARGE) || openSpace[cell]))
				return cell;
		}
		return -1;
	}
}
