package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class DrainingStrike extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.DRAINING_STRIKE);
	private static final int COST = 2;
	private static final int BASE_DAMAGE = 6;
	private static final int UPGRADED_DAMAGE = 2;
	
	public DrainingStrike() {
		super(CardNames.DRAINING_STRIKE, cardStrings.NAME, CardPaths.DRAINING_STRIKE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.isMultiDamage = true;
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeDamage(UPGRADED_DAMAGE);
			rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int damageInstances = 0;
        final AbstractMonster[] monsters = new AbstractMonster[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
        AbstractDungeon.getCurrRoom().monsters.monsters.toArray(monsters);
        for (int i = 0; i < monsters.length; ++i) {
            if (monsters[i] != null && !monsters[i].isDying && !monsters[i].isEscaping) {
                int tmp = this.multiDamage[i] - monsters[i].currentBlock;
                if (tmp > 0 || this.upgraded) {
                	damageInstances++;
                }
            }
        }
		
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AttackEffect.BLUNT_LIGHT));
		if (damageInstances > 0){
            AbstractDungeon.player.gainEnergy(damageInstances);
            AbstractDungeon.actionManager.updateEnergyGain(damageInstances);
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                c.triggerOnGainEnergy(damageInstances, true);
            }
		}
	}

}
