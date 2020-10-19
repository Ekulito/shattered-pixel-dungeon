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

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.FinalGoo;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.Emitter.Factory;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class FinalGooSprite extends MobSprite {

	private Animation idleBig, idleMedium, idleSmall;
	private Animation runBig, runMedium, runSmall;
	private Animation attackBig, attackMedium, attackSmall;
	private Animation dieBig, dieMedium, dieSmall;

	public enum GooSize{
		BIG, MEDIUM, SMALL
	}

	private GooSize curGooSize = null;

	public FinalGooSprite() {
		super();

		texture( Assets.Sprites.FINALGOO );

		TextureFilm frames = new TextureFilm( texture, 20, 14 );

		idleBig = new Animation( 10, true );
		idleBig.frames( frames, 2, 1, 0, 0, 1 );

		runBig = new Animation( 15, true );
		runBig.frames( frames, 3, 2, 1, 2 );

		attackBig = new Animation( 10, false );
		attackBig.frames( frames, 8, 9, 10 );

		dieBig = new Animation( 10, false );
		dieBig.frames( frames, 5, 6, 7 );

		int c = 12;

		idleMedium = new Animation( 10, true );
		idleMedium.frames( frames, c+2, c+1, c+0, c+0, c+1 );

		runMedium = new Animation( 15, true );
		runMedium.frames( frames, c+3, c+2, c+1, c+2 );

		attackMedium = new Animation( 10, false );
		attackMedium.frames( frames, c+8, c+9, c+10 );

		dieMedium = new Animation( 10, false );
		dieMedium.frames( frames, c+5, c+6, c+7 );

		c = 24;

		idleSmall = new Animation( 10, true );
		idleSmall.frames( frames, c+2, c+1, c+0, c+0, c+1 );

		runSmall = new Animation( 15, true );
		runSmall.frames( frames, c+3, c+2, c+1, c+2 );

		attackSmall = new Animation( 10, false );
		attackSmall.frames( frames, c+8, c+9, c+10 );

		dieSmall = new Animation( 10, false );
		dieSmall.frames( frames, c+5, c+6, c+7 );

		setGooSize(GooSize.BIG);

		play(idle);
	}

	private void switchAnim() {
		if(curAnim == dieBig || curAnim == dieMedium || curAnim == dieSmall)
			play(die);
		else if(curAnim == attackBig || curAnim == attackMedium || curAnim == attackSmall)
			play(attack);
		else if(curAnim == runBig || curAnim == runMedium || curAnim == runSmall)
			play(run);
		else play(idle);
	}

	public void setGooSize(GooSize size) {
		if(size == curGooSize)
			return;
		curGooSize = size;
		if(size == GooSize.BIG) {
			idle = idleBig;
			run = runBig;
			attack = attackBig;
			die = dieBig;
		}
		if(size == GooSize.MEDIUM) {
			idle = idleMedium;
			run = runMedium;
			attack = attackMedium;
			die = dieMedium;
		}
		if(size == GooSize.SMALL) {
			idle = idleSmall;
			run = runSmall;
			attack = attackSmall;
			die = dieSmall;
		}
		switchAnim();
	}

	@Override
	public void link(Char ch) {
		super.link(ch);
		if(ch instanceof FinalGoo)
			((FinalGoo)ch).updateGooSize();
	}

	@Override
	public void play(Animation anim) {
		super.play(anim);
	}

	@Override
	public int blood() {
		return 0xFF000000;
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void onComplete( Animation anim ) {
		super.onComplete(anim);
	}
}
