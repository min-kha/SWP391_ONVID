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

import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.exception.InvalidInputException;
import group5.swp391.onlinelearning.model.admin.TopicDto;
import group5.swp391.onlinelearning.service.ITopicService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@Controller
@RequestMapping(value = "/admin/topics")
public class TopicController {

    @Autowired
    private ITopicService topicService;
    @Autowired
    private ThymeleafBaseCRUD thymeleafBaseCRUD;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/index")
    public String getIndex(Model model) {
        List<Topic> topics = topicService.getTopics();
        String title = "List topics - Admin";
        thymeleafBaseCRUD.setBaseForList(model, topics, title);
        return "sample/index";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {
        TopicDto topic = new TopicDto();
        thymeleafBaseCRUD.setBaseForEntity(model, topic, "Create Topic - Admin");
        return "sample/create";
    }

    @PostMapping("/create")
    public String postCreate(@Valid @ModelAttribute("entity") TopicDto topicDto, BindingResult bindingResult,
            Model model) {
        final String title = "Create Topic - Admin";
        Topic topic = new Topic();
        try {
            if (bindingResult.hasErrors()) {
                thymeleafBaseCRUD.setBaseForEntity(model, topicDto, title);
                return "/sample/create";
            }
            topic = modelMapper.map(topicDto, Topic.class);
            topicService.addTopic(topic);
        } catch (InvalidInputException e) {
            bindingResult.rejectValue(e.getFieldName(), e.getErrorCode(), e.getMessage());
            thymeleafBaseCRUD.setBaseForEntity(model, topicDto, title);
            return "/sample/create";
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/topics/index";
    }

    @GetMapping("/edit/{id}")
    public String getEdit(Model model, @PathVariable @NotNull int id) {
        Topic topic = topicService.getTopicById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, topic, "Edit Topic - Admin");
        return "sample/edit";
    }

    @PostMapping("/edit/{id}")
    public String postEdit(@Valid @ModelAttribute("entity") Topic topic, BindingResult bindingResult,
            Model model) {
        final String title = "Edit Topic - Admin";
        try {
            if (bindingResult.hasErrors()) {
                thymeleafBaseCRUD.setBaseForEntity(model, topic, title);
                return "/sample/edit";
            }
            topicService.updateTopic(topic);
        } catch (InvalidInputException e) {
            bindingResult.rejectValue(e.getFieldName(), e.getErrorCode(), e.getMessage());
            thymeleafBaseCRUD.setBaseForEntity(model, topic, title);
            return "/sample/edit";
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/topics/index";
    }

    @GetMapping("/delete/{id}")
    public String getDelete(Model model, @PathVariable @NotNull int id) {
        Topic topic = topicService.getTopicById(id);
        thymeleafBaseCRUD.setBaseForEntity(model, topic, "Confirm delete topic - Admin");
        return "sample/delete";
    }

    @PostMapping("/delete/{id}")
    public String postDelete(Model model, @PathVariable @NotNull int id) {
        try {
            topicService.deleteTopic(id);
        } catch (Exception e) {
            return "/error";
        }
        return "redirect:/admin/topics/index";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(Model model, @PathVariable @NotNull int id) {
        try {
            Topic topic = topicService.getTopicById(id);
            thymeleafBaseCRUD.setBaseForEntity(model, topic, "Detail topic - Admin");
        } catch (Exception e) {
            return "/error";
        }
        return "sample/detail";
    }
}
