package fr.diptrack.web.dtos;

public class MarkDto {

	private String mail;
	private float noteCC;
	private float notePartiel;
	private float idSubject;
	
	public MarkDto(float noteCC,float notePartiel, String mail, float idSubject){
			this.mail = mail;
			this.noteCC = noteCC;
			this.notePartiel = notePartiel;
			this.idSubject = idSubject;
		}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public float getNoteCC() {
		return noteCC;
	}
	
	public void setNoteCC(float noteCC) {
		this.noteCC = noteCC;
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