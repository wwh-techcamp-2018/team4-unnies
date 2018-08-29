package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.*;
import com.baemin.nanumchan.exception.NotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;

    public Page<Order> receivedProducts(Long participantId, Pageable pageable) {
        return orderRepository.findAllByParticipantIdOrderByIdDesc(participantId, pageable);
    }

    public Order changeOrderStatus(User user, Long orderId, Status status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(("신청 내역이 존재하지 않습니다")));

        if (!order.getProduct().getOwner().isSameUser(user)) {
            throw new NotAllowedException("요리사 본인이 아닙니다");
        }

        order.changeStatus(status);

        return orderRepository.save(order);
    }

    public List<Order> getOrders(User user, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(("상품이 존재하지 않습니다")));

        if (!user.isSameUser(product.getOwner())) {
            throw new NotAllowedException("요리사 본인이 아닙니다");
        }

        return orderRepository.findAllByProduct(product);
    }
}
