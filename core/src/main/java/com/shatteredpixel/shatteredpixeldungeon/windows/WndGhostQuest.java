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

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.MobGhost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FetidRatSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GhostSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GnollTricksterSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GreatCrabSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class WndGhostQuest extends Window {

	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;
	private static final float GAP		= 2;

	public WndGhostQuest( final Ghost ghost, String text, Mob questBoss ) {

		super();

		IconTitle titlebar = new IconTitle();
		RenderedTextBlock message;
		titlebar.icon(new GhostSprite());
		titlebar.label(Messages.titleCase(ghost.name()));
		message=PixelScene.renderTextBlock(text,6);

		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );

		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );

		RedButton btnAccept=new RedButton(Messages.get(this,"accept")) {
			@Override
			protected void onClick() {acceptQuest(ghost, questBoss);}
		};
		btnAccept.setRect( 0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT );
		add(btnAccept);

		RedButton btnAvoid=new RedButton(Messages.get(this,"avoid")) {
			@Override
			protected void onClick() {avoidQuest(ghost);}
		};
		btnAvoid.setRect( 0, btnAccept.bottom() + GAP, WIDTH, BTN_HEIGHT );
		add(btnAvoid);

		RedButton btnReject=new RedButton(Messages.get(this,"reject")) {
			@Override
			protected void onClick() {rejectQuest(ghost);}
		};
		btnReject.setRect( 0, btnAvoid.bottom() + GAP, WIDTH, BTN_HEIGHT );
		add(btnReject);

		resize(WIDTH, (int) btnReject.bottom());
	}

	private void acceptQuest(Ghost ghost, Mob questBoss) {
		hide();
		Ghost.Quest.given=true;
		GameScene.add(questBoss);
	}
	private void avoidQuest(Ghost ghost){
		hide();
	}
	private void rejectQuest(Ghost ghost){
		hide();
		MobGhost mobGhost=new MobGhost();
		mobGhost.pos=ghost.pos;
		ghost.sprite.killAndErase();
		ghost.destroy();
		GameScene.add(mobGhost);
		mobGhost.yell(Messages.get(mobGhost,"onfight"));
	}
}
