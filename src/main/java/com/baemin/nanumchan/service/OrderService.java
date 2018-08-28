package com.baemin.nanumchan.service;

import com.baemin.nanumchan.domain.Order;
import com.baemin.nanumchan.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Page<Order> receivedProducts(Long participantId, Pageable pageable) {
        return orderRepository.findAllByParticipantIdOrderByIdDesc(participantId, pageable);
    }
}
