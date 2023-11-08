package com.work.ecart.service;

import com.work.ecart.dto.CartReqDto;
import com.work.ecart.util.GenericResponse;
import com.work.ecart.exception.ResourceNotFoundException;

public interface CartService {
    GenericResponse saveCart(CartReqDto cartReqDto) throws ResourceNotFoundException;
}
