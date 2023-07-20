package group5.swp391.onlinelearning.controller.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import group5.swp391.onlinelearning.entity.Topic;
import group5.swp391.onlinelearning.model.dto.CourseDtoHomeDetail;
import group5.swp391.onlinelearning.service.ITopicService;
import group5.swp391.onlinelearning.service.impl.CourseService;
import group5.swp391.onlinelearning.utils.PagingUtils;

@Controller
@RequestMapping(value = "/student/course")
public class SearchCourseController {

        @Autowired
        CourseService courseService;

        @Autowired
        ITopicService topicService;

        @Autowired
        PagingUtils pagingUtils;

        @GetMapping("/search")
        public String getSearchListByKeyword(@RequestParam("keyword") String keyword,
                        @RequestParam("pageChoose") String pageChoose,
                        Model model) {
                List<CourseDtoHomeDetail> courseDtoHomeDetailsSearch = courseService.getSearchCourse(keyword);
                int numberPerPage = 9;
                List<CourseDtoHomeDetail> listOnPage = (List<CourseDtoHomeDetail>) pagingUtils.getPagingList(pageChoose,
                                courseDtoHomeDetailsSearch, numberPerPage);
                int numberOfPage = pagingUtils.getNumberOfPage(courseDtoHomeDetailsSearch, numberPerPage);
                int pageChooseInt = Integer.parseInt(pageChoose);

                List<Integer> listPageNumber = pagingUtils.getListPageNumber(numberOfPage);

                List<Topic> topics = topicService.getTopics();
                model.addAttribute("topics", topics);
                model.addAttribute("courses", listOnPage);
                model.addAttribute("keyword", keyword);
                model.addAttribute("pageChoose", pageChooseInt);
                model.addAttribute("listPageNumber", listPageNumber);

                return "/student/search/search-list";
        }

        @GetMapping("/searchByPrice")
        public String getSearchListByPrice(@RequestParam("from") String from, @RequestParam("to") String to,
                        @RequestParam("pageChoose") String pageChoose,
                        Model model) {

                Double fromDouble = Double.parseDouble(from);
                Double toDouble = Double.parseDouble(to);

                List<CourseDtoHomeDetail> courseDtoHomeDetailsSearchByPrice = courseService.getCourseByPrice(fromDouble,
                                toDouble);
                int numberPerPage = 9;
                List<CourseDtoHomeDetail> listOnPage = (List<CourseDtoHomeDetail>) pagingUtils.getPagingList(pageChoose,
                                courseDtoHomeDetailsSearchByPrice, numberPerPage);
                int numberOfPage = pagingUtils.getNumberOfPage(courseDtoHomeDetailsSearchByPrice, numberPerPage);
                int pageChooseInt = Integer.parseInt(pageChoose);
                List<Integer> listPageNumber = pagingUtils.getListPageNumber(numberOfPage);

                List<Topic> topics = topicService.getTopics();
                model.addAttribute("topics", topics);
                model.addAttribute("courses", listOnPage);
                model.addAttribute("from", from);
                model.addAttribute("to", to);
                model.addAttribute("pageChoose", pageChooseInt);
                model.addAttribute("listPageNumber", listPageNumber);
                return "/student/search/search-list-by-price";
        }
}
