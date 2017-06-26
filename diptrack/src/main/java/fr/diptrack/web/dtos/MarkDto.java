package fr.diptrack.web.dtos;

public class MarkDto {

	private String mail;
	private String lastName;
	private String firstName;
	private Long idMarkCc;
	private Long idMarkExam;
	private float markCc;
	private float markExam;
	private long idSubject;
	
	public MarkDto(){
		
	}
	
	public MarkDto(String mail, String lastName, String firstName, float markCc,float markExam, Long idSubject, Long idMarkCc, Long idMarkExam){
		this.lastName = lastName ;
		this.firstName = firstName ;
		this.mail = mail;
		this.markCc = markCc;
		this.markExam = markExam;
		this.idSubject = idSubject;
		this.idMarkCc = idMarkCc;
		this.idMarkExam = idMarkExam;
	}
	
	public MarkDto(float markCc,float markExam, String mail, long idSubject){
			this.mail = mail;
			this.markCc = markCc;
			this.markExam = markExam;
			this.idSubject = idSubject;
		}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getIdMarkCc() {
		return idMarkCc;
	}

	public void setIdMarkCc(Long idMarkCc) {
		this.idMarkCc = idMarkCc;
	}

	public Long getIdMarkExam() {
		return idMarkExam;
	}

	public void setIdMarkExam(Long idMarkExam) {
		this.idMarkExam = idMarkExam;
	}

	public float getMarkCc() {
		return markCc;
	}

	public void setMarkCc(float markCc) {
		this.markCc = markCc;
	}

	public float getMarkExam() {
		return markExam;
	}

	public void setMarkExam(float markExam) {
		this.markExam = markExam;
	}

	public long getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(long idSubject) {
		this.idSubject = idSubject;
	}


	

}