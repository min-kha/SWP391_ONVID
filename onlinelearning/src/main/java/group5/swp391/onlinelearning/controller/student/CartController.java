package group5.swp391.onlinelearning.controller.student;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import group5.swp391.onlinelearning.entity.Cart;
import group5.swp391.onlinelearning.entity.Course;
import group5.swp391.onlinelearning.entity.User;
import group5.swp391.onlinelearning.service.Impl.CartService;
import group5.swp391.onlinelearning.service.Impl.CourseService;

@Controller
@RequestMapping("/student")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    CourseService courseService;

    @PostMapping("/addToCart")
    public String addToCart(HttpSession session, HttpServletRequest request) {
        int CourseId = Integer.parseInt(request.getParameter("courseId"));
        User student = (User) session.getAttribute("studentSession");
        cartService.addCourseToCart(cartService.getCartByStudentId(student.getId()),
                courseService.getCourseByCourseId(CourseId));
        Cart cart = cartService.getCartByStudentId(student.getId());
        session.setAttribute("cartStudentSession", cartService.getCoursebyCartId(cart.getId()));
        return "redirect:/student/course/detail/" + CourseId;
    }

    @GetMapping("/cart/detail")
    public String cartDetail(HttpSession session, Model model) {
        List<Course> courses = (List<Course>) session.getAttribute("cartStudentSession");
        model.addAttribute("courses", courses);
        BigDecimal total = BigDecimal.ZERO;
        for (Course c : courses) {
            total = total.add(c.getPrice());
        }
        model.addAttribute("total", total);
        model.addAttribute("size", courses.size());
        return "student/cart/cart";
    }

    @GetMapping("/cart/delete/{courseId}")
    public String deleteCourseInCart(Model model, @PathVariable Integer courseId,
            HttpSession session) {
        User student = (User) session.getAttribute("studentSession");
        Cart cart = cartService.getCartByStudentId(student.getId());
        cartService.deleteCourseInCartByCourseId(courseId, cart.getId());
        session.setAttribute("cartStudentSession",
                cartService.getCoursebyCartId(cart.getId()));
        return "redirect:/student/cart/detail";
    }

    @PostMapping("/cart/pay")
    public String deleteAllCourseInCart(HttpSession session) {
        User student = (User) session.getAttribute("studentSession");
        Cart cart = cartService.getCartByStudentId(student.getId());
        List<Course> courses = (List<Course>) session.getAttribute("cartStudentSession");
        cartService.deleteAllCourseInCart(courses, cart.getId());
        session.setAttribute("cartStudentSession",
                cartService.getCoursebyCartId(cart.getId()));
        return "forward:/student/my-course";
    }
}
