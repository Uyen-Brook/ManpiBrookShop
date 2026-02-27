package com.manpibrook.backend_api.service.user;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.manpibrook.backend_api.dto.request.user.OrderIssueRequest;
import com.manpibrook.backend_api.entity.OrderGHN;
import com.manpibrook.backend_api.entity.OrderIssue;
import com.manpibrook.backend_api.entity.enums.EOrderStatus;
import com.manpibrook.backend_api.repository.OrderGHNRepository;
import com.manpibrook.backend_api.repository.OrderIssueRepository;
import com.manpibrook.backend_api.utils.BusinessException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderIssueService {

    private final OrderGHNRepository orderGHNRepository;
    private final OrderIssueRepository orderIssueRepository;

    /**
     * Khách tạo yêu cầu hủy / hoàn hàng.
     * Lưu log để nhân viên xử lý, đồng thời cập nhật trạng thái đơn.
     */
    public void createIssue(Long customerId, OrderIssueRequest request) {
        OrderGHN order = orderGHNRepository.findById(request.getOrderId())
                .orElseThrow(() -> new BusinessException("Không tìm thấy đơn hàng"));

        // Cập nhật trạng thái đơn
        if (request.getType().name().equals("CANCEL")) {
            order.setStatus(EOrderStatus.CANCELLED);
        } else {
            // RETURN: để status vẫn CONFIRMED/DELIVERED, nhân viên sẽ xử lý tiếp
            // hoặc bạn có thể thêm trạng thái riêng nếu cần.
        }
        order.setUpdatedAt(LocalDateTime.now());
        orderGHNRepository.save(order);

        // Lưu yêu cầu để nhân viên xem
        OrderIssue issue = new OrderIssue();
        issue.setOrderGhnId(order.getOrderGhnId());
        issue.setCustomerId(customerId);
        issue.setType(request.getType());
        issue.setReason(request.getReason());
        issue.setCreatedAt(LocalDateTime.now());
        issue.setHandled(false);

        orderIssueRepository.save(issue);
    }
}