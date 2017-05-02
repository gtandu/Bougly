package fr.bougly.service.helper;

import java.util.ArrayList;
import java.util.List;

import fr.bougly.web.dtos.AccountDto;

public class AccountDtoListFromExcel {
	
	private List listAccountDto;
	
	public AccountDtoListFromExcel()
	{
		listAccountDto = new ArrayList();
	}
	
	
	public void add(AccountDto account)
	{
		for(int i=0; i<listAccountDto.size(); i++)
		{
			if(listAccountDto.get(i).equals(account))
			{
				account.setErrorExcel(true);
			}
		}
		this.listAccountDto.add(account);
	}


	public List<AccountDto> getListAccountDto() {
		return listAccountDto;
	}


	public void setListAccountDto(ArrayList listAccountDto) {
		this.listAccountDto = listAccountDto;
	}
	
	

}
