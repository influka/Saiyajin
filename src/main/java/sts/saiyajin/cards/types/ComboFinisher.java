package sts.saiyajin.cards.types;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.powers.Combo;

public abstract class ComboFinisher extends CustomCard {

	public ComboFinisher(String id, String name, String img, int cost, String rawDescription, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
	}
	
	public abstract void updatedComboChain(int amount);
	
	@Override
	public void triggerOnCardPlayed(AbstractCard cardPlayed) {
		super.triggerOnCardPlayed(cardPlayed);
		AbstractPlayer player = AbstractDungeon.player;
		if(cardPlayed instanceof ComboFinisher && player.hasPower(PowerNames.COMBO)){
			Combo comboPower = (Combo) player.getPower(PowerNames.COMBO);
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Combo(player, -comboPower.amount), -comboPower.amount));
		}
	}
}