package fr.diptrack.service.helper;

import java.util.ArrayList;
import java.util.List;

import fr.diptrack.model.MccRule;
import fr.diptrack.model.Subject;
import fr.diptrack.web.dtos.MccRuleDto;

public class ConvertListMccRulesDtoToClass {

	public static List<MccRule> convert(List<MccRuleDto> listMccRuleDto, Subject subject) {
		List<MccRule> listMccRules = new ArrayList<>();
		for (MccRuleDto mccRuleDto : listMccRuleDto) {
			MccRule mccRule = new MccRule(mccRuleDto, subject);
			listMccRules.add(mccRule);
		}
		return listMccRules;

	}

}
