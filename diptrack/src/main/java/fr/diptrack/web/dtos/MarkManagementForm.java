package fr.diptrack.web.dtos;

import java.util.List;

public class MarkManagementForm {

		List<MarkDto> listMarkDto ;
		
		public MarkManagementForm(){
			
		}

		public List<MarkDto> getListMarkDto() {
			return listMarkDto;
		}

		public void setListMarkDto(List<MarkDto> listMarkDto) {
			this.listMarkDto = listMarkDto;
		}
}