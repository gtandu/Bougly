package fr.diptrack.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fr.diptrack.model.enumeration.MarkTypeEnum;
import fr.diptrack.web.dtos.MccRuleDto;

@Entity
public class MccRule {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private int coefficient;
	@Enumerated(EnumType.STRING)
	private MarkTypeEnum markType;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Subject subject;

	public MccRule() {
	}

	public MccRule(MccRuleDto mccRuleDto, Subject subject) {
		this.name = mccRuleDto.getName();
		this.coefficient = mccRuleDto.getCoefficient();
		this.markType = MarkTypeEnum.valueOf(MarkTypeEnum.class, mccRuleDto.getMarkType());
		this.subject = subject;
	}

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

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	public MarkTypeEnum getMarkType() {
		return markType;
	}

	public void setMarkType(MarkTypeEnum markType) {
		this.markType = markType;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}