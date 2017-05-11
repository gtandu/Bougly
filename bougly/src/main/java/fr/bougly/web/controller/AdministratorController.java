package fr.bougly.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.bougly.exception.StudentNumberExistException;
import fr.bougly.exception.UserExistException;
import fr.bougly.model.UserAccount;
import fr.bougly.model.enumeration.RoleAccountEnum;
import fr.bougly.service.AccountService;
import fr.bougly.service.helper.MapperBeanUtil;
import fr.bougly.web.dtos.AccountDto;

@Controller
@RequestMapping(value = "/administrateur")
public class AdministratorController {

	public static final String LIST_ACCOUNT_FROM_EXCEL_FILE = "listAccountFromExcelFile";
	private static final String URL_ADMINISTRATOR_CONTROLLER = "/administrateur";
	public static final String URL_MANAGE_ACCOUNT = "/gestionCompte.html";
	public static final String URL_CREATE_ACCOUNT = "/creerCompte.html";
	public static final String URL_DELETE_ACCOUNT = "/supprimerCompte.html";
	public static final String URL_EDIT_ACCOUNT = "/editerCompte.html";
	public static final String URL_UPLOAD_EXCEL_FILE = "/uploadExcel.html";

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = URL_MANAGE_ACCOUNT, method = RequestMethod.GET)
	public ModelAndView showPageManageAccount(@RequestParam(defaultValue = "1", required = true) Integer pageNumber) {
		Page<UserAccount> pageAccountList = accountService.listAllByPage(pageNumber);
		List<UserAccount> accountList = pageAccountList.getContent();
		ArrayList<AccountDto> accountDtoList = MapperBeanUtil.convertAccountListToAccountDtoList(accountList);

		int current = pageAccountList.getNumber() + 1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, pageAccountList.getTotalPages());

		ModelAndView model = new ModelAndView("gestionCompte");

		model.addObject("accountDtoList", accountDtoList);
		model.addObject("beginIndex", begin);
		model.addObject("endIndex", end);
		model.addObject("currentIndex", current);
		return model;

	}

	@RequestMapping(value = URL_CREATE_ACCOUNT, method = RequestMethod.GET)
	public ModelAndView showPageCreateAccount(ModelAndView model,
			@RequestParam(name = "error", required = false) boolean error, @ModelAttribute("mail") String mail,
			@ModelAttribute("studentNumber") String studentNumber) {
		if (error) {
			model.addObject("error", error);
			if (mail != null) {
				model.addObject("mailExist", mail);
			}
			if (studentNumber != null) {
				model.addObject("studentNumberExist", studentNumber);
			}
		}
		model.setViewName("creerCompte");
		model.addObject("accountDto", new AccountDto());
		model.addObject("allRoles", RoleAccountEnum.listesAllRoles());
		return model;

	}

	@RequestMapping(value = URL_CREATE_ACCOUNT, method = RequestMethod.POST)
	public String createAccountFromDtoData(@ModelAttribute(value = "accountDto") AccountDto accountDto,
			HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
		try {
			accountService.saveUserAccountAndPublishEventRegistration(accountDto, request);
			return "redirect:" + URL_ADMINISTRATOR_CONTROLLER + URL_MANAGE_ACCOUNT;
		} catch (UserExistException e) {
			redirectAttributes.addFlashAttribute("mail", accountDto.getMail());
			return "redirect:" + URL_ADMINISTRATOR_CONTROLLER + URL_CREATE_ACCOUNT + "?error=true";
		} catch (StudentNumberExistException e) {
			redirectAttributes.addFlashAttribute("studentNumber", accountDto.getStudentNumber());
			return "redirect:" + URL_ADMINISTRATOR_CONTROLLER + URL_CREATE_ACCOUNT + "?error=true";
		}

	}

	@RequestMapping(value = URL_UPLOAD_EXCEL_FILE, method = RequestMethod.POST)
	public String handleExcelFileUpload(@RequestParam("file") MultipartFile accountExcelFile,
			HttpServletRequest request) throws Exception {

		ArrayList<AccountDto> listAccountFromExcelFile = accountService.createAccountFromExcelFile(accountExcelFile,
				request);
		HttpSession session = request.getSession();
		session.setAttribute(LIST_ACCOUNT_FROM_EXCEL_FILE, listAccountFromExcelFile);

		return "redirect:" + URL_ADMINISTRATOR_CONTROLLER + URL_UPLOAD_EXCEL_FILE;
	}

	@RequestMapping(value = URL_UPLOAD_EXCEL_FILE, method = RequestMethod.GET)
	public ModelAndView showResultPageFromExcelFile(HttpServletRequest request) throws Exception {

		final HttpSession session = request.getSession();
		ModelAndView model = new ModelAndView("resultatCreationComptes");

		model.addObject(LIST_ACCOUNT_FROM_EXCEL_FILE, session.getAttribute(LIST_ACCOUNT_FROM_EXCEL_FILE));

		return model;
	}

	@RequestMapping(value = URL_DELETE_ACCOUNT, method = RequestMethod.POST)
	@ResponseBody
	public void deleteAccount(@RequestParam(required = true) String mail) {
		accountService.deleteAccountByMail(mail);

	}

	// TODO A refactorer classe Controlleur parente
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		StringTrimmerEditor stringtrimmer = new StringTrimmerEditor(true);
		binder.registerCustomEditor(String.class, stringtrimmer);
	}

	@RequestMapping(value = URL_EDIT_ACCOUNT, method = RequestMethod.POST)
	@ResponseBody
	public String editAccountFromDto(AccountDto accountDto) {
		accountService.editAccountFromCompteBean(accountDto);
		return "OK";
	}

}
