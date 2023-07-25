package group5.swp391.onlinelearning;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import group5.swp391.onlinelearning.controller.admin.WithdrawalDetailController;
import group5.swp391.onlinelearning.entity.WithdrawalDetail;
import group5.swp391.onlinelearning.service.IWithdrawalDetailService;
import group5.swp391.onlinelearning.utils.ThymeleafBaseCRUD;

class WithdrawalDetailAdminControllerTest {

    @Mock
    private IWithdrawalDetailService withdrawalDetailService;

    @Mock
    private ThymeleafBaseCRUD thymeleafBaseCRUD;

    @InjectMocks
    private WithdrawalDetailController withdrawalDetailController;

    @Mock
    private Model model;

    @Mock
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetIndex() {
        // Arrange
        List<WithdrawalDetail> withdrawalDetails = new ArrayList<>();
        // Add some withdrawalDetail objects to the list
        WithdrawalDetail wd1 = new WithdrawalDetail();
        WithdrawalDetail wd2 = new WithdrawalDetail();
        withdrawalDetails.add(wd1);
        withdrawalDetails.add(wd2);

        when(withdrawalDetailService.getWithdrawalDetailsToReview()).thenReturn(withdrawalDetails);

        // Act
        String result = withdrawalDetailController.getIndex(model, httpSession);

        // Assert
        assertEquals("admin/withdrawalDetail/index", result);
        verify(model).addAttribute("session", httpSession);
        verify(thymeleafBaseCRUD).setBaseForList(model, withdrawalDetails, "List Withdrawals - Admin");
    }

    @Test
    void testGetDetail() {
        // Arrange
        int id = 1;
        WithdrawalDetail withdrawalDetail = new WithdrawalDetail();
        when(withdrawalDetailService.getWithdrawalDetailById(id)).thenReturn(withdrawalDetail);

        // Act
        String result = withdrawalDetailController.getDetail(model, id);

        // Assert
        assertEquals("admin/withdrawalDetail/detail", result);
        verify(thymeleafBaseCRUD).setBaseForEntity(model, withdrawalDetail, "Detail Withdrawal - Admin");
    }

    @Test
    void testGetReview() throws Exception {
        // Arrange
        int id = 1;
        WithdrawalDetail withdrawalDetail = new WithdrawalDetail();
        withdrawalDetail.setStatus(0);
        when(withdrawalDetailService.getWithdrawalDetailById(id)).thenReturn(withdrawalDetail);

        // Act
        String result = withdrawalDetailController.getReview(model, id, httpSession);

        // Assert
        assertEquals("admin/withdrawalDetail/review", result);
        verify(withdrawalDetailService).updateWithdrawalDetail(withdrawalDetail);
        verify(thymeleafBaseCRUD).setBaseForEntity(model, withdrawalDetail, "Review Withdrawal - Admin");
    }

    @Test
    void testPostReviewApproved() throws Exception {
        // Arrange
        int id = 1;
        WithdrawalDetail withdrawalDetail = new WithdrawalDetail();
        withdrawalDetail.setStatus(1);
        when(withdrawalDetailService.getWithdrawalDetailById(id)).thenReturn(withdrawalDetail);

        // Act
        String result = withdrawalDetailController.postReview(model, id, "true");

        // Assert
        assertEquals(2, withdrawalDetail.getStatus());
        verify(withdrawalDetailService).updateWithdrawalDetail(withdrawalDetail);
        verify(thymeleafBaseCRUD).setBaseForEntity(model, withdrawalDetail, "Detail Withdrawal - Admin");
    }

    @Test
    void testPostReviewRejected() throws Exception {
        // Arrange
        int id = 1;
        WithdrawalDetail withdrawalDetail = new WithdrawalDetail();
        withdrawalDetail.setStatus(1);
        when(withdrawalDetailService.getWithdrawalDetailById(id)).thenReturn(withdrawalDetail);

        // Act
        String result = withdrawalDetailController.postReview(model, id, "false");

        // Assert
        assertEquals(3, withdrawalDetail.getStatus());
        verify(withdrawalDetailService).updateWithdrawalDetail(withdrawalDetail);
        verify(thymeleafBaseCRUD).setBaseForEntity(model, withdrawalDetail, "Detail Withdrawal - Admin");
    }

    @Test
    void testPostReviewInvalidApproveValue() throws Exception {
        // Arrange
        int id = 1;
        WithdrawalDetail withdrawalDetail = new WithdrawalDetail();
        withdrawalDetail.setStatus(1);
        when(withdrawalDetailService.getWithdrawalDetailById(id)).thenReturn(withdrawalDetail);

        // Act
        String result = withdrawalDetailController.postReview(model, id, "invalid_value");

        // Assert
        // assertEquals("redirect:/admin/withdrawalDetails/detail/{id}" + id, result);
        // The status should not change if the "approve" value is invalid
        assertEquals(1, withdrawalDetail.getStatus());
        verify(withdrawalDetailService, never()).updateWithdrawalDetail(withdrawalDetail);
        verify(thymeleafBaseCRUD).setBaseForEntity(model, withdrawalDetail, "Detail Withdrawal - Admin");
    }
}
