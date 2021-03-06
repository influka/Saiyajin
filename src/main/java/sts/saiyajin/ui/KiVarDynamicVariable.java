package sts.saiyajin.ui;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;
import sts.saiyajin.cards.types.SaiyanCard;

public class KiVarDynamicVariable extends DynamicVariable {

	final static String KI_VARIABLE_KEY = "Saiyan:KiVar";
	
	@Override
	public String key() {
		return KI_VARIABLE_KEY;
	}

	@Override
	public boolean isModified(AbstractCard card) {
		return false;
	}

	@Override
	public int value(AbstractCard card) {
		if (card instanceof SaiyanCard) {
			return ((SaiyanCard)card).kiVariable;
		}
		return 0;
	}

	@Override
	public int baseValue(AbstractCard card) {
		if (card instanceof SaiyanCard) {
			return ((SaiyanCard)card).kiVariable;
		}
		return 0;
	}

	@Override
	public boolean upgraded(AbstractCard card) {
		return card.upgraded;
	}
}
