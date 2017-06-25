package fr.diptrack.web.dtos;

import java.util.List;

public class SubjectDto {

	private long id;
	private String name;
	private String description;
	private int coefficient;
	private int threshold;
	private boolean resit;
	private int year;
	private long ueId;
	private String previousName;
	private List<MccRuleDto> listMccRulesDto;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public boolean isResit() {
		return resit;
	}

	public void setResit(boolean resit) {
		this.resit = resit;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public long getUeId() {
		return ueId;
	}

	public void setUeId(long ueId) {
		this.ueId = ueId;
	}

	public String getPreviousName() {
		return previousName;
	}

	public void setPreviousName(String previousName) {
		this.previousName = previousName;
	}

	public List<MccRuleDto> getListMccRulesDto() {
		return listMccRulesDto;
	}

	public void setListMccRulesDto(List<MccRuleDto> listMccRulesDto) {
		this.listMccRulesDto = listMccRulesDto;
	}

}
