package fr.bougly.service.helper;

import java.util.ArrayList;
import java.util.List;

import fr.bougly.model.UserAccount;
import fr.bougly.web.dtos.AccountDto;

public class MapperBeanUtil {
	
	public static ArrayList<AccountDto> convertAccountListToAccountDtoList(List<UserAccount> accountList)
	{
		ArrayList<AccountDto> accountDtoList = new ArrayList<>();
		
		for(UserAccount account : accountList)
		{
			AccountDto accountDto = new AccountDto(account);
			accountDtoList.add(accountDto);
		}
		
		return accountDtoList;
	}

}
