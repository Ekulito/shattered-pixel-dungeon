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

package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.MobGhost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.MobWandmaker;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GhostSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.WandmakerSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

public class WndWandmakerQuest extends Window {

	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;
	private static final float GAP		= 2;

	public WndWandmakerQuest(final Wandmaker wandmaker, String text ) {

		super();

		IconTitle titlebar = new IconTitle();
		RenderedTextBlock message;
		titlebar.icon(new WandmakerSprite());
		titlebar.label(Messages.titleCase(wandmaker.name()));
		message=PixelScene.renderTextBlock(text,6);

		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );

		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );

		RedButton btnAccept=new RedButton(Messages.get(this,"accept")) {
			@Override
			protected void onClick() {acceptQuest(wandmaker);}
		};
		btnAccept.setRect( 0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT );
		add(btnAccept);

		RedButton btnAvoid=new RedButton(Messages.get(this,"avoid")) {
			@Override
			protected void onClick() {avoidQuest(wandmaker);}
		};
		btnAvoid.setRect( 0, btnAccept.bottom() + GAP, WIDTH, BTN_HEIGHT );
		add(btnAvoid);

		RedButton btnReject=new RedButton(Messages.get(this,"reject")) {
			@Override
			protected void onClick() {rejectQuest(wandmaker);}
		};
		btnReject.setRect( 0, btnAvoid.bottom() + GAP, WIDTH, BTN_HEIGHT );
		add(btnReject);

		resize(WIDTH, (int) btnReject.bottom());
	}

	private void acceptQuest(Wandmaker wandmaker) {
		hide();
		Wandmaker.Quest.given=true;
	}
	private void avoidQuest(Wandmaker wandmaker){
		hide();
	}
	private void rejectQuest(Wandmaker wandmaker){
		hide();
		MobWandmaker mobWandmaker=new MobWandmaker();
		mobWandmaker.pos=wandmaker.pos;
		wandmaker.dieWithoutAnimation();
		GameScene.add(mobWandmaker);
		//things to avoid bad wands
		Wand cw=Wandmaker.Quest.wand1;
		if(cw instanceof WandOfBlastWave || cw instanceof WandOfLivingEarth || cw instanceof WandOfTransfusion || cw instanceof WandOfWarding)
			Wandmaker.Quest.wand1=new WandOfCorruption();
		Wandmaker.Quest.wand1.userAsChar=mobWandmaker;
		cw=Wandmaker.Quest.wand2;
		if(cw instanceof WandOfBlastWave || cw instanceof WandOfLivingEarth || cw instanceof WandOfTransfusion || cw instanceof WandOfWarding)
			Wandmaker.Quest.wand2=new WandOfFrost();
	}
}
