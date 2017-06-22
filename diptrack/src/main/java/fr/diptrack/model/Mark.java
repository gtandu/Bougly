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

@Entity
public class Mark {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private float mark;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Student student;
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Subject subject;
	@Enumerated(EnumType.STRING)
	private MarkTypeEnum markTypeEnum;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getMark() {
		return mark;
	}
	public void setMark(float mark) {
		this.mark = mark;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public MarkTypeEnum getMarkTypeEnum() {
		return markTypeEnum;
	}
	public void setMarkTypeEnum(MarkTypeEnum markTypeEnum) {
		this.markTypeEnum = markTypeEnum;
	}
	
	
	
	

}
