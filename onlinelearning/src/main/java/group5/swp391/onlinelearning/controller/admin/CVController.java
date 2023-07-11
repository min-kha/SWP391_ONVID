package group5.swp391.onlinelearning.controller.admin;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.CV;
import group5.swp391.onlinelearning.exception.InvalidInputException;
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

    // @GetMapping("/edit/{id}")
    // public String getEdit(Model model, @PathVariable @NotNull int id) {
    // CV cV = cVService.getCVById(id);
    // thymeleafBaseCRUD.setBaseForEntity(model, cV, "Edit CV - Admin");
    // return "sample/edit";
    // }

    // @PostMapping("/edit/{id}")
    // public String postEdit(@Valid @ModelAttribute("entity") CV cV, BindingResult
    // bindingResult,
    // Model model) {
    // final String title = "Edit CV - Admin";
    // try {
    // if (bindingResult.hasErrors()) {
    // thymeleafBaseCRUD.setBaseForEntity(model, cV, title);
    // return "/sample/edit";
    // }
    // cVService.updateCV(cV);
    // } catch (InvalidInputException e) {
    // bindingResult.rejectValue(e.getFieldName(), e.getErrorCode(),
    // e.getMessage());
    // thymeleafBaseCRUD.setBaseForEntity(model, cV, title);
    // return "/sample/edit";
    // } catch (Exception e) {
    // return "/error";
    // }
    // return "redirect:/admin/cVs/index";
    // }

    // @PostMapping("/delete/{id}")
    // public String postDelete(Model model, @PathVariable @NotNull int id) {
    // try {
    // cVService.deleteCV(id);
    // } catch (Exception e) {
    // return "/error";
    // }
    // return "redirect:/admin/cVs/index";
    // }

    @GetMapping("/detail/{id}")
    public String getDetail(Model model, @PathVariable @NotNull int id) {
        CV cV = cVService.getCVById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, cV, "Detail CV - Admin");
        return "admin/cv/detail";
    }

    @GetMapping("/review/{id}")
    public String getReview(Model model, @PathVariable @NotNull int id) {
        return "redirect:/admin/users/details/{id}";
        // try {
        // CV cV = cVService.getCVById(id);
        // thymeleafBaseCRUD.setBaseForEntity(model, cV, "Review CV - Admin");
        // } catch (Exception e) {
        // return "/error";
        // }
        // return "admin/cv/edit";
    }
}
