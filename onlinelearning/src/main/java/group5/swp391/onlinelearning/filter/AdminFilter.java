package group5.swp391.onlinelearning.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import group5.swp391.onlinelearning.entity.User;

import java.io.IOException;

@WebFilter("/admin/users/*")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Kiểm tra vai trò của người dùng
        User user = (User) httpRequest.getSession().getAttribute("user");
        String requestURI = httpRequest.getRequestURI();
        boolean allowAccess = false; // Biến để theo dõi xem có cho phép truy cập không

        if (requestURI.equals("/admin/login")) {
            allowAccess = true;
        }

        if (!allowAccess && user != null) {
            int role = user.getRole();
            if (role == 3) {
                // Cho phép truy cập vào các trang bắt đầu bằng "/admin/user/*"
                allowAccess = true;
            } else {
                // Redirect hoặc trả về thông báo lỗi nếu không có quyền truy cập
                httpResponse.sendRedirect("/access-denied");
                return;
            }
        }

        if (allowAccess) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect("/admin/login");
        }
    }
}
