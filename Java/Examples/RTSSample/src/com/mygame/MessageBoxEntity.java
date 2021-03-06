﻿package com.mygame;

import loon.LSystem;
import loon.Touch;
import loon.action.sprite.SpriteBatch;
import loon.action.sprite.painting.DrawableEvent;
import loon.core.geom.Vector2f;
import loon.core.graphics.device.LColor;
import loon.core.graphics.device.LColorPool;
import loon.core.graphics.device.LFont;
import loon.core.graphics.opengl.LTexture;
import loon.core.timer.GameTime;


public class MessageBoxEntity extends GameEntity
{

	public DrawableEvent Accepted;
	private LFont font;

	private String text;
	private Vector2f textOrigin;
	private LTexture texture;
	private Vector2f textureOrigin;

	public MessageBoxEntity(String message)
	{
		this.text = message;
		super.setIsPopup(true);
		super.setTransitionOnTime(0.2f);
		super.setTransitionOffTime(0.2f);
	}

	public MessageBoxEntity(LTexture background, String message)
	{
		this(message);
		this.texture = background;
	}

	@Override
	public void Draw(SpriteBatch batch,GameTime gameTime)
	{
		super.getScreenManager().FadeBackBufferToBlack(batch,(super.getTransitionAlpha() * 1f) / 3f);
		Vector2f vector = new Vector2f(LSystem.screenRect.width/2,LSystem.screenRect.height/2);
		LColor color = LColorPool.$().getColor(1f,1f,1f,super.getTransitionAlpha());
	
		if (this.texture != null)
		{
			batch.draw(this.texture, vector.sub(this.textureOrigin), color);
		}
		if (this.text != null)
		{
			batch.drawString(this.font, this.text, vector.sub(this.textOrigin), color);
		}
	}

	@Override
	public void HandleInput()
	{
		if (Touch.isDown()||Touch.isUp())
		{
			if (this.Accepted != null)
			{
				Accepted.invoke();
			}
			super.ExitScreen();
		}
	}

	@Override
	public void LoadContent()
	{
		this.font = super.getScreenManager().getGameContent().gameFont;
		if (this.texture != null)
		{
			this.textureOrigin = new Vector2f( this.texture.getWidth(),  this.texture.getHeight()).div();
		}
		if (this.text != null)
		{
			this.textOrigin = font.getOrigin(this.text);
		}
	}
}