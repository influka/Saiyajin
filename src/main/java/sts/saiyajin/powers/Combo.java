package sts.saiyajin.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.ui.PowerPaths;

public class Combo extends AbstractPower {

	public static final String POWER_ID = PowerNames.COMBO;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public Combo(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.img = new Texture(PowerPaths.COMBO);
		this.canGoNegative = false;
	}

	@Override
	public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		super.onApplyPower(power, target, source);
		if (power instanceof Combo){
			for (AbstractCard c : AbstractDungeon.player.hand.group) {
				if (c instanceof ComboFinisher) ((ComboFinisher) c).updatedComboChain(power.amount);
			}
			for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
				if (c instanceof ComboFinisher) ((ComboFinisher) c).updatedComboChain(power.amount);
			}
			for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
				if (c instanceof ComboFinisher) ((ComboFinisher) c).updatedComboChain(power.amount);
			}
		}
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}