package com.work.ecart.service;

import com.work.ecart.dto.*;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.util.GenericResponse;

public interface OrderService {


    GenericResponse saveOrder(OrderReqDto orderReqDto) throws ResourceNotFoundException;

    GenericResponse getAllOrders(Integer pageNo, Integer pageSize);

    GenericResponse getOrderByPayment(String payment);
}
