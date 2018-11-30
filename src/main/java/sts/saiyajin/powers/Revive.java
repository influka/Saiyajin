package sts.saiyajin.powers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.ui.PowerPaths;

public class Revive extends AbstractPower {

	public static final String POWER_ID = PowerNames.REVIVE;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public static final Logger logger = LogManager.getLogger(Revive.class);
	
	private int buffAmount = 0;

	public Revive(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = -1;
		this.buffAmount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.img = new Texture(PowerPaths.COMBO);
		this.canGoNegative = false;
	}
	
	@Override
	public int onAttacked(DamageInfo info, int damageAmount) {
		if (!this.owner.hasPower(PowerNames.REVIVE) || this.owner.currentHealth > damageAmount) return super.onAttacked(info, damageAmount);
		for(AbstractPower power : this.owner.powers){
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, power));
		}
		
		this.owner.heal(this.owner.maxHealth);
		((AbstractMonster)this.owner).usePreBattleAction();
		int invincibleAmount = (owner.maxHealth / 3 ) +1;
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, buffAmount), buffAmount));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new InvinciblePower(owner, invincibleAmount), invincibleAmount));
		AbstractDungeon.actionManager.addToTop(new StunMonsterAction((AbstractMonster) owner, owner, 1));
		AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.COMMON);
		logger.info("################### ON ENEMY DEATH  FINISH #########");
		return 0;
	}
	
	
	public void updateDescription() {
		//this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	}
}