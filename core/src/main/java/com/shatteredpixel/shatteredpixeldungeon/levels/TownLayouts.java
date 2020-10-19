/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation,  either version 3 of the License, or
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

public class TownLayouts {

	//32X32
	//idk what that 32X32 means, it's 48x48 actually
	private static final int W = Terrain.WALL;
	private static final int T = Terrain.BARRICADE;
	private static final int Z = Terrain.HIGH_GRASS;
	private static final int D = Terrain.DOOR;
	private static final int L = Terrain.LOCKED_DOOR;
	private static final int e = Terrain.EMPTY; //for readability

	private static final int E = Terrain.EMPTY;
	private static final int X = Terrain.EXIT;

	private static final int M = Terrain.WALL_DECO;
	private static final int P = Terrain.PEDESTAL;
	private static final int F = Terrain.EMPTY_DECO;
	private static final int O = Terrain.EMPTY_SP;
	private static final int A = Terrain.WELL;
	private static final int B = Terrain.BOOKSHELF;

	private static final int U = Terrain.STATUE;
	private static final int S = Terrain.SECRET_DOOR;




	public static final int[] TOWN_LAYOUT =	{
			M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
			M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
			M, 	M, 	M, 	M,  e, 	M, 	T, 	M, 	M, 	M, 	M, 	M,  e,  e, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,  e, 	M,  e, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e, 	W, 	S, 	W, 	W, 	W,  e,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M, 	M, 	M, 	M,  e, 	M, 	M,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e, 	W, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	W, 	F, 	F, 	F, 	F, 	W, 	M, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e, 	W, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	W, 	W, 	W, 	W, 	W,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	F, 	F, 	F, 	F, 	W, 	M, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	F, 	F, 	F, 	F, 	W, 	M, 	M, 	M, 	M,
			M, 	M,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	B, 	F, 	F, 	F, 	F, 	W, 	M, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	U, 	U, 	U, 	O, 	W,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	B, 	F, 	F, 	F, 	F, 	W, 	M, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	U, 	O, 	O, 	O, 	W,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W,  e,  e,  e,  e, 	L, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	U, 	O, 	O, 	O, 	W,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e, 	M, 	M, 	M, 	M, 	M,
			M, 	M, 	M, 	M,  e,  e, 	W, 	W, 	W, 	D, 	W, 	W, 	O, 	O, 	O, 	W,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M, 	M,
			M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	W,  e,  e,  e, 	W, 	W, 	W, 	L, 	W, 	W, 	O, 	O, 	O, 	W,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M, 	M,
			M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	O, 	O, 	W,  e,  e,  e,  e, 	W, 	W, 	W, 	W, 	W,  e,  e,  e,  e,  e, 	M, 	M, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	W, 	W, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	W, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	W, 	W, 	M, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	O, 	O, 	W, 	W, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	E,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	W, 	O, 	O, 	U, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	D, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	W, 	O, 	O, 	U, 	U, 	U, 	U, 	U, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	W, 	W, 	W,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	B, 	B, 	B, 	W,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	W,  e,  e,  e,  e, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M,
			M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	W, 	W,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M, 	M, 	M,
			M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M, 	M,
			M, 	M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M,
			M, 	M, 	M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	L, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	P, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M,
			M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M,
			M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W,  e,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M,
			M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	W, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M,
			M, 	M, 	W, 	F, 	F, 	F, 	F, 	W, 	T,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	O, 	O, 	O, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	M, 	M,
			M, 	M, 	W, 	F, 	F, 	F, 	F, 	T, 	T, 	T, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	B, 	B, 	B, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M,
			M, 	M, 	W, 	F, 	F, 	F, 	F, 	T, 	T, 	T, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	W, 	W, 	W,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	M, 	M, 	M, 	M,
			M, 	M, 	W, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M,
			M, 	M, 	W, 	F, 	F, 	X, 	F, 	F, 	F, 	F, 	F, 	W, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	W, 	M, 	M, 	M,
			M, 	M, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	W, 	M, 	M, 	M,
			M, 	M, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	M, 	M, 	M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	W, 	M, 	M, 	M,
			M, 	M, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	M, 	M, 	M, 	M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e,  e, 	M, 	M,  e,  e,  e,  e,  e,  e,  e,  e,  e, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	W, 	M, 	M, 	M,
			M, 	M, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,  e,  e,  e,  e,  e, 	M, 	M, 	M, 	M, 	M,  e,  e,  e,  e,  e,  e, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M,
			M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,  e,  e,  e,  e, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,  e,  e,  e, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
			M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
			M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M


	};

}