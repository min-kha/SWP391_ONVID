package group5.swp391.onlinelearning.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.entity.CV;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.ICVService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@Controller
@RequestMapping("/admin/cvs")
public class CVController {

    @Autowired
    private ICVService cVService;
    @Autowired
    private ThymeleafBaseCRUD thymeleafBaseCRUD;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/index")
    public String getIndex(Model model) {
        List<CV> cVs = cVService.getCVs();
        String title = "List CVs - Admin";
        thymeleafBaseCRUD.setBaseForList(model, cVs, title);
        return "admin/cv/index";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(Model model, @PathVariable @NotNull int id) {
        CV cV = cVService.getCVById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, cV, "Detail CV - Admin");
        return "admin/cv/detail";
    }

    @GetMapping("/review/{id}")
    public String getReview(Model model, @PathVariable @NotNull int id, HttpSession session) throws Exception {
        try {
            CV cV = cVService.getCVById(id);
            User user = (User) session.getAttribute("user");
            if (cV.getStatus() == 0) {
                cV.setStaff(user);
                cV.setStatus(1); // set status to In progress
                cVService.updateCV(cV);
            }
            thymeleafBaseCRUD.setBaseForEntity(model, cV, "Review CV - Admin");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "admin/cv/review";
    }

    @PostMapping("/review/{id}")
    public String postReview(Model model, @PathVariable @NotNull int id, @RequestParam String approve) {
        CV cV = cVService.getCVById(id);
        try {
            if (approve.equals("true")) {
                cV.setStatus(2); // set status to approved
                cVService.updateCV(cV);

            }
            if (approve.equals("false")) {
                cV.setStatus(3); // set status to rejected
                cVService.updateCV(cV);
            }
            thymeleafBaseCRUD.setBaseForEntity(model, cV, "Detail CV - Admin");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "redirect:/admin/cvs/detail/{id}";
    }
}
