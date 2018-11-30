package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class GenkiDama extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.GENKI_DAMA);
	private static final int COST = 3;
	private static final int BASE_DAMAGE = 0;
	private int BASE_KI_DAMAGE_MULTIPLIER = 1;
	
	public GenkiDama() {
		super(CardNames.GENKI_DAMA, cardStrings.NAME, CardPaths.SAIYAN_STRIKE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.baseMagicNumber = BASE_KI_DAMAGE_MULTIPLIER;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeMagicNumber(1);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		Saiyajin kakarot = (Saiyajin) player;
		Ki kiPower = (Ki) kakarot.getPower(PowerNames.KI);
		int damage = kiPower.amount * this.magicNumber;
		int multiDamage[] = DamageInfo.createDamageMatrix(damage, true);
		
		DamageAllEnemiesAction damageAction = new DamageAllEnemiesAction(
				player, multiDamage, damageTypeForTurn, AttackEffect.SMASH);
		AbstractDungeon.actionManager.addToBottom(damageAction);
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, -kiPower.amount), -kiPower.amount));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WeakPower(player, 2, false), 2));
	}

}