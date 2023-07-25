package group5.swp391.onlinelearning.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import group5.swp391.onlinelearning.entity.User;

import java.io.IOException;

@WebFilter(urlPatterns = { "", "" })
public class TeacherFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Kiểm tra vai trò của người dùng
        User user = (User) httpRequest.getSession().getAttribute("user");
        String requestURI = httpRequest.getRequestURI();
        boolean allowAccess = false; // Biến để theo dõi xem có cho phép truy cập không
        if (requestURI.equals("/teacher/login")) {
            allowAccess = true;
        }
        if (requestURI.equals("/teacher/register")) {
            allowAccess = true;
        }
        if (requestURI.equals("/teacher/login/watting")) {
            allowAccess = true;
        }

        if (!allowAccess && user != null) {
            int role = user.getRole();
            if (role == 1) {
                // Cho phép truy cập vào các trang bắt đầu bằng "/admin/*"
                allowAccess = true;
            } else {
                // Redirect hoặc trả về thông báo lỗi nếu không có quyền truy cập
                httpResponse.sendRedirect("/access-denied");
            }
        }

        if (allowAccess) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect("/teacher/login");
        }

    }

    // Các phương thức khác của Interface Filter
    // ...
}