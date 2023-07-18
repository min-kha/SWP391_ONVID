package group5.swp391.onlinelearning.controller.teacher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.model.dto.UserDTORegisterRequest;
import group5.swp391.onlinelearning.service.EmailSenderService;
import group5.swp391.onlinelearning.service.ICVService;
import group5.swp391.onlinelearning.service.ICartService;
import group5.swp391.onlinelearning.service.IUserService;
import group5.swp391.onlinelearning.service.IWalletService;
import group5.swp391.onlinelearning.service.OtpService;

@Controller
@RequestMapping("/teacher/")
public class RegisterTeacherController {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private IUserService userService;
    @Autowired
    ICartService cartService;
    @Autowired
    OtpService otpService;
    @Autowired
    IWalletService walletService;
    @Autowired
    ICVService cvService;

    @GetMapping("/register")
    public String getMethodRegister(Model model) {
        model.addAttribute("user", new UserDTORegisterRequest());
        return "teacher/register/register";
    }

    @PostMapping("/register")
    public String checkValidAccount(@Valid @ModelAttribute("user") UserDTORegisterRequest userDTORegisterRequest,
            BindingResult bindingResult, HttpServletRequest request, HttpSession session) throws Exception {
        if (userService.getUserByEmail(userDTORegisterRequest.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email.exist", "Email address is duplicated");
        }
        if (!userDTORegisterRequest.getPassword().equals(userDTORegisterRequest.getRePassword())) {

            bindingResult.rejectValue("rePassword", "error.repass.notSam", "Repassword is not same with password");

        }
        if (bindingResult.hasErrors()) {
            return "teacher/register/register";
        }
        emailSenderService.sendEmail(userDTORegisterRequest.getEmail(),
                "is an code verify ONVID account of you",
                "The OTP to authenticate your account is: ");
        session.setAttribute("userRegisterSession", userDTORegisterRequest);
        long otpCreationTime = System.currentTimeMillis();
        session.setAttribute("otpCreationTime", otpCreationTime);
        return "teacher/register/input-otp";
    }

    @PostMapping("/store-account")
    public String storeAccount(HttpSession session) {
        UserDTORegisterRequest userDTORegisterRequest = (UserDTORegisterRequest) session
                .getAttribute("userRegisterSession");
        User user = userService.addTeacherRegister(userDTORegisterRequest);
        walletService.createWallet(user.getId());
        session.invalidate();
        return "redirect:/teacher/register/cv/" + user.getId();
    }

    @PostMapping("/check-otp")
    public String checkOtp(HttpServletRequest request, Model model) {
        String otpCodeInput = request.getParameter("otpCodeInput");
        boolean checkValidOtp = otpService.checkValidOtp(otpCodeInput, model);
        if (checkValidOtp) {
            return "forward:/teacher/store-account";
        } else {
            return "teacher/register/input-otp";
        }
    }

    @GetMapping("/register/cv/{id}")
    public String getMethodUploadCv(Model model, @PathVariable int id) {
        // tao moi cv
        model.addAttribute("teacherId", id);
        model.addAttribute("errorFormat", "");
        return "teacher/register/cv";
    }

    @PostMapping("/register/cv/{id}")
    public String postMethodUploadCv(HttpServletRequest req, Model model,
            @PathVariable int id) throws IOException, ServletException {
        // Alalys cv
        // get link image
        String projectPath = System.getProperty("user.dir");
        String fileName = "";
        Part filePart = req.getPart("cv");
        fileName = filePart.getSubmittedFileName();
        if (fileName.endsWith(".pdf")) {
            String relativePath = "\\src\\main\\resources\\static\\pdf\\cv";

            String filePath = projectPath + relativePath + File.separator + fileName;
            Path path = Paths.get(filePath);

            try (InputStream inputStream = filePart.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                return "404";
            }
            // fileName is not valid ( != jpg and png files)
        } else {
            model.addAttribute("teacherId", id);
            model.addAttribute("errorFormat", "Error formatting of Cv (must be pdf)");
            return "teacher/register/cv";
        }
        // save cv
        User user = userService.getUserById(id);
        cvService.createNewCV(user, fileName);
        return "teacher/register/watting";
    }
}