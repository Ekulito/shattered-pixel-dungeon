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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.MobGhost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class MobGhostSprite extends MobSprite {

	private Animation blink;
	public MobGhostSprite() {
		super();
		
		texture( Assets.Sprites.GHOST );

		TextureFilm frames = new TextureFilm( texture, 14, 15 );

		idle = new Animation( 5, true );
		idle.frames( frames, 0, 1 );

		run = new Animation( 10, true );
		run.frames( frames, 0, 1 );

		attack = new Animation( 10, false );
		attack.frames( frames, 0, 2, 3 );

		zap = attack.clone();

		die = new Animation( 8, false );
		die.frames( frames, 0, 4, 5, 6, 7 );

		blink = new Animation(15,false);
		blink.frames(frames,7, 6, 5, 4, 0);
		
		play( idle );
	}
	
	public void zap( int cell ) {
		
		turnTo( ch.pos , cell );
		play( zap );

		MagicMissile.boltFromChar( parent,
				MagicMissile.BEACON,
				this,
				cell,
				new Callback() {
					@Override
					public void call() {
						((MobGhost)ch).onZapComplete();
					}
				} );
		Sample.INSTANCE.play( Assets.Sounds.ZAP );
	}

	public void blink( int from, int to ) {

		place( to );

		play( blink );
		turnTo( from , to );

		isMoving = true;

		ch.onMotionComplete();
	}
	
	@Override
	public void onComplete( Animation anim ) {
		if (anim == blink) {
			isMoving = false;
			idle();
		}
		if (anim == zap) {
			idle();
		}
		super.onComplete( anim );
	}

	@Override
	public void draw() {
		Blending.setLightMode();
		super.draw();
		Blending.setNormalMode();
	}

	@Override
	public void die() {
		super.die();
		emitter().start( ShaftParticle.FACTORY, 0.3f, 4 );
		emitter().start( Speck.factory( Speck.LIGHT ), 0.2f, 3 );
	}

	@Override
	public int blood() {
		return 0xFFFFFF;
	}
}
