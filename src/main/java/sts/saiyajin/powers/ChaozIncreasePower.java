package sts.saiyajin.powers;


import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.actions.ChaozAction;
import sts.saiyajin.utils.PowerNames;

public class ChaozIncreasePower extends AbstractPower implements InvisiblePower {

	public static final String POWER_ID = PowerNames.CHAOZ;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	final Logger logger = LogManager.getLogger(ChaozIncreasePower.class);
	 final private int incAmount;
	 final private UUID targetUUID;
	 private static int chaozIncreaseOffset = 0;
	 private int counter;
	 
	public ChaozIncreasePower(final AbstractCreature owner, final int turns, final int incAmount, final UUID targetUUID) {
		this.name = NAME;
		this.owner = owner;
		this.amount = -1;
		this.counter = turns;
		chaozIncreaseOffset++;
		this.ID = POWER_ID + chaozIncreaseOffset;
		this.type = AbstractPower.PowerType.BUFF;
        this.description = DESCRIPTIONS[0];
		this.canGoNegative = false;
		this.incAmount = incAmount;
		this.targetUUID = targetUUID;
		this.loadRegion("combust");
	}
	
	
	@Override
	public void flash() {}
	
	@Override
	public void flashWithoutSound() {}
	
	@Override
	public void stackPower(int stackAmount) {
		counter+=stackAmount;
	}
	@Override
	public void reducePower(int reduceAmount) {}
	
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            counter--;
            if (counter == 0) {
                AbstractDungeon.actionManager.addToBottom(new ChaozAction(incAmount, targetUUID));
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this.ID));
            }
        }
    }

    @Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}