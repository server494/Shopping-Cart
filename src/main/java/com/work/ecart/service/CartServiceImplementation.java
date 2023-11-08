package com.work.ecart.service;

import com.work.ecart.dto.CartReqDto;
import com.work.ecart.dto.CartResDto;
import com.work.ecart.util.GenericResponse;
import com.work.ecart.entity.Cart;
import com.work.ecart.entity.Customer;
import com.work.ecart.entity.Product;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.repository.CartRepository;
import com.work.ecart.repository.CustomerRepository;
import com.work.ecart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplementation implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public GenericResponse saveCart(CartReqDto cartReqDto) throws ResourceNotFoundException {

        Product product = productRepository.findById(cartReqDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", cartReqDto.getProductId()));

        Customer customer = customerRepository.findById(cartReqDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", cartReqDto.getCustomerId()));

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setProduct(product);
        cartRepository.save(cart);

        CartResDto cartResDto = new CartResDto();
        cartResDto.setCustomerName(customer.getName());
        cartResDto.setCustomerPhone(customer.getPhoneNumber());
        cartResDto.setCustomerAddress(customer.getAddress());
        cartResDto.setProductName(product.getName());
        GenericResponse genericResponse = new GenericResponse<>();
        genericResponse.setData(cartResDto);
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Product added to cart");
        return genericResponse;
    }
}
