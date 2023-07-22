package group5.swp391.onlinelearning.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import group5.swp391.onlinelearning.entity.User;

import java.io.IOException;

public class StudentFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Kiểm tra vai trò của người dùng
        User user = (User) httpRequest.getSession().getAttribute("user");
        if (user != null) {
            int role = user.getRole();
            if (role == 0) {
                // Cho phép truy cập vào các trang bắt đầu bằng "/admin/*"
                chain.doFilter(request, response);
            } else {
                // Redirect hoặc trả về thông báo lỗi nếu không có quyền truy cập
                httpResponse.sendRedirect("/access-denied");
            }
        } else {
            httpResponse.sendRedirect("/access-denied");
        }

    }

    // Các phương thức khác của Interface Filter
    // ...
}
