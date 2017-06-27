package fr.diptrack.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.diptrack.model.Mark;
import fr.diptrack.model.Student;
import fr.diptrack.model.Subject;
import fr.diptrack.model.Teacher;
import fr.diptrack.model.UserAccount;
import fr.diptrack.model.enumeration.MarkTypeEnum;
import fr.diptrack.service.AccountService;
import fr.diptrack.service.MarkService;
import fr.diptrack.service.StudentService;
import fr.diptrack.service.SubjectService;
import fr.diptrack.service.TeacherService;
import fr.diptrack.service.mail.PublishMarkMail;
import fr.diptrack.web.dtos.MarkDto;
import fr.diptrack.web.dtos.MarkManagementForm;

@Controller
@RequestMapping(value = "/enseignant")
public class TeacherController {

	public static final String URL_HOME_PAGE_TEACHER = "/homePageTeacher.html";
	public static final String URL_NOTE_GRADE_MANAGEMENT = "/gestionNote.html";
	public static final String URL_MAIL_PUBLICATION_NOTE_ENVOYE = "/mailPublicationNoteEnvoye.html";

	@Autowired
	public AccountService accountService;

	@Autowired
	public TeacherService teacherService;

	@Autowired
	public StudentService studentService;

	@Autowired
	public SubjectService subjectService;

	@Autowired
	public PublishMarkMail publishMarkMail;

	@Autowired
	public MarkService markService;

	@RequestMapping(value = URL_HOME_PAGE_TEACHER, method = RequestMethod.GET)
	public ModelAndView showPageHomePageteacher() {
		ModelAndView model = new ModelAndView("homePageTeacher");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserAccount account = accountService.findByMail(auth.getName());

		model.addObject("account", account);

		return model;
	}

	@RequestMapping(value = URL_NOTE_GRADE_MANAGEMENT, method = RequestMethod.GET)
	public ModelAndView showPageNoteGradeManagement(@RequestParam(required = false, value = "save") boolean save) {
		ModelAndView model = new ModelAndView("gestionNote");

		MarkManagementForm markManagementForm = new MarkManagementForm();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Teacher teacher = teacherService.findByMail(auth.getName());
		String subjectName = teacher.getSubject();

		List<Student> listStudents = studentService.findAllStudentBySubject(subjectName);
		List<MarkDto> listMarkDto = new ArrayList<>();

		for (Student student : listStudents) {
			float markCc = 0;
			float markExam = 0;
			Long idSubject = null;
			Long idMarkCc = null;
			Long idMarkExam = null;
			for (Mark mark : student.getListMarks()) {

				if (MarkTypeEnum.Continu.getMarkType().equals(mark.getMarkTypeEnum().getMarkType())) {
					markCc = mark.getMark();
					idMarkCc = mark.getId();
				} else if (MarkTypeEnum.Partiel.getMarkType().equals(mark.getMarkTypeEnum().getMarkType())) {
					markExam = mark.getMark();
					idMarkExam = mark.getId();
				}
			}
			for (Subject subject : student.getListSubjects()) {
				idSubject = subject.getId();
			}

			MarkDto mark = new MarkDto(student.getMail(), student.getFirstName(), student.getLastName(), markCc,
					markExam, idSubject, idMarkCc, idMarkExam);
			listMarkDto.add(mark);
		}

		markManagementForm.setListMarkDto(listMarkDto);

		model.addObject("markManagementForm", markManagementForm);
		model.addObject("save", save);
		return model;

	}

	@RequestMapping(value = URL_NOTE_GRADE_MANAGEMENT, method = RequestMethod.POST, params = { "save" })
	public String saveMarks(@ModelAttribute(value = "markManagementForm") MarkManagementForm markManagementForm) {

		List<MarkDto> listMarkDto = markManagementForm.getListMarkDto();

		saveMarks(listMarkDto);

		return "redirect:/enseignant" + URL_NOTE_GRADE_MANAGEMENT + "?save=true";
	}

	@RequestMapping(value = URL_NOTE_GRADE_MANAGEMENT, method = RequestMethod.POST, params = { "publish" })
	public String publishMark(@ModelAttribute(value = "markManagementForm") MarkManagementForm markManagementForm) {
		List<MarkDto> listMarkDto = markManagementForm.getListMarkDto();

		saveAndPublishMarks(listMarkDto);

		return "redirect:/enseignant" + URL_MAIL_PUBLICATION_NOTE_ENVOYE;
	}

	private void saveMarks(List<MarkDto> listMarkDto) {
		for (MarkDto markDto : listMarkDto) {
			Student student = (Student) accountService.findByMail(markDto.getMail());
			Subject subject = subjectService.findById(markDto.getIdSubject());
			Mark markCc = null;
			Mark markExam = null;
			List<Mark> studentListMark = student.getListMarks();
			if (markDto.getIdMarkCc() != null) {
				markCc = markService.updateMark(markDto.getIdMarkCc(), markDto.getMarkCc());
			} else {
				markCc = new Mark(markDto.getMarkCc(), student, subject, MarkTypeEnum.Continu);
				markCc = markService.saveMark(markCc);
				studentListMark.add(markCc);
			}

			if (markDto.getIdMarkExam() != null) {

				markExam = markService.updateMark(markDto.getIdMarkExam(), markDto.getMarkExam());
			} else {
				markExam = new Mark(markDto.getMarkExam(), student, subject, MarkTypeEnum.Partiel);
				markExam = markService.saveMark(markExam);
				studentListMark.add(markExam);
			}

			student.setListMarks(studentListMark);
			studentService.saveStudent(student);

		}

	}

	private void saveAndPublishMarks(List<MarkDto> listMarkDto) {
		for (MarkDto markDto : listMarkDto) {
			Student student = (Student) accountService.findByMail(markDto.getMail());
			Subject subject = subjectService.findById(markDto.getIdSubject());
			Mark markCc = null;
			Mark markExam = null;
			List<Mark> studentListMark = student.getListMarks();
			if (markDto.getIdMarkCc() != null) {
				markCc = markService.updateMark(markDto.getIdMarkCc(), markDto.getMarkCc());
			} else {
				markCc = new Mark(markDto.getMarkCc(), student, subject, MarkTypeEnum.Continu);
				markCc = markService.saveMark(markCc);
				studentListMark.add(markCc);
			}

			if (markDto.getIdMarkExam() != null) {

				markExam = markService.updateMark(markDto.getIdMarkExam(), markDto.getMarkExam());
			} else {
				markExam = new Mark(markDto.getMarkExam(), student, subject, MarkTypeEnum.Partiel);
				markExam = markService.saveMark(markExam);
				studentListMark.add(markExam);
			}

			student.setListMarks(studentListMark);
			studentService.saveStudent(student);

			publishMarkMail.sendMailPublishMarks(markDto.getMail(), subject.getName(), markCc.getMark(),
					markExam.getMark());
		}

	}

	@RequestMapping(value = URL_MAIL_PUBLICATION_NOTE_ENVOYE, method = RequestMethod.GET)
	public ModelAndView showMailPublicationNoteEnvoye() {
		return new ModelAndView("mailPublicationNoteEnvoye");
	}
}