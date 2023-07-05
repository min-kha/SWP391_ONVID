package group5.swp391.onlinelearning.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.service.admin.ITopicService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

@Controller
@RequestMapping(value = "/admin/topics")
public class TopicController {

    @Autowired
    private ITopicService topicService;
    @Autowired
    private ThymeleafBaseCRUD thymeleafBaseCRUD;

    @GetMapping("/index")
    public String getIndex(Model model) {
        List<Topic> topics = topicService.getTopics();
        String title = "List topics - Admin";
        thymeleafBaseCRUD.setBaseForList(model, topics, title);
        return "sample/index";
    }

    @GetMapping(value = "/topic")
    public String getMethodTopic(Model model) {
        model.addAttribute("topic", new Topic());
        return "#";
    }

    @PostMapping(value = "/topic")
    public String postMethodTopic(@Valid @ModelAttribute("topic") Topic topic, BindingResult bindingResult,
            Model model) {
        if (!topicService.addTopics(topic)) {
            model.addAttribute("topic", topic);
            bindingResult.rejectValue("name", "error.duplicate", "Name is exit in systerm");
            return "#";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("topic", topic);
            return "#";
        }
        return "#";
    }

}
