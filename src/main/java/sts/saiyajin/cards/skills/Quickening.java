package sts.saiyajin.cards.skills;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import sts.saiyajin.cards.types.KiCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class Quickening extends KiCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.QUICKENING);

	private static final int COST = 0;
	private static final int KI_CONSUMPTION = 10;
	private static final int UPGRADED_KI_CONSUMPTION = -6;
	private static final int PLATED_ARMOR_AMOUNT = 1;
	private static final int UPGRADED_PLATED_ARMOR_AMOUNT = 1;
	private int platedArmor;
	
	final Logger logger = LogManager.getLogger(Quickening.class);
	
	public Quickening() {
		super(CardNames.QUICKENING, cardStrings.NAME, CardPaths.QUICKENING, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ALL);
		this.baseMagicNumber = KI_CONSUMPTION;
		this.magicNumber = this.baseMagicNumber;
		this.kiRequired = KI_CONSUMPTION;
		this.platedArmor = PLATED_ARMOR_AMOUNT;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_KI_CONSUMPTION);
			this.platedArmor += UPGRADED_PLATED_ARMOR_AMOUNT;
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		logger.info("USE QUICKENING WITH PLATED ARMOR : " + platedArmor);
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PlatedArmorPower(player, platedArmor), platedArmor));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, -kiRequired), -kiRequired));
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, player, new VulnerablePower(mo, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
        }
	}

}
