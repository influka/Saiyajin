package sts.saiyajin.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.ui.PowerPaths;

public class Ki extends AbstractPower {

	public static final String POWER_ID = PowerNames.KI;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
//	private static final int KI_REGEN_AMOUNT = 3;

	public Ki(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.img = new Texture(PowerPaths.KI);
		this.canGoNegative = false;
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
	
//	@Override
//	public void atStartOfTurn() {
//		super.atStartOfTurn();
//		AbstractPlayer player = AbstractDungeon.player;
//		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, KI_REGEN_AMOUNT), KI_REGEN_AMOUNT));
//	}
}