package fr.diptrack.web.dtos;

public class MarkDto {

	private String mail;
	private String lastName;
	private String firstName;
	private float noteCc;
	private float notePartiel;
	private float idSubject;
	
	public MarkDto(String mail, String lastName, String firstName, float noteCC,float notePartiel){
		this.lastName = lastName ;
		this.firstName = firstName ;
		this.mail = mail;
		this.noteCc = noteCC;
		this.notePartiel = notePartiel;
	}
	
	public MarkDto(float noteCC,float notePartiel, String mail, float idSubject){
			this.mail = mail;
			this.noteCc = noteCC;
			this.notePartiel = notePartiel;
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
		
	public float getNoteCc() {
		return noteCc;
	}

	public void setNoteCc(float noteCc) {
		this.noteCc = noteCc;
	}
	
	public float getNotePartiel() {
		return notePartiel;
	}
	public void setNotePartiel(float notePartiel) {
		this.notePartiel = notePartiel;
	}

	public float getIdSubject() {
		return idSubject;
	}

	public void setIdSubject(float idSubject) {
		this.idSubject = idSubject;
	}

}