package com.work.ecart.service;

import com.work.ecart.dto.GenericResponse;
import com.work.ecart.dto.OrderReqDto;
import com.work.ecart.dto.OrderResDto;
import com.work.ecart.entity.Cart;
import com.work.ecart.entity.Customer;
import com.work.ecart.entity.Orders;
import com.work.ecart.entity.Product;
import com.work.ecart.exception.ResourceNotFoundException;
import com.work.ecart.repository.CartRepository;
import com.work.ecart.repository.CustomerRepository;
import com.work.ecart.repository.OrderRepository;
import com.work.ecart.repository.ProductRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GenericResponse saveOrder(OrderReqDto orderReqDto) throws ResourceNotFoundException {

        Cart cart = cartRepository.findById(orderReqDto.getCartId())
                .orElseThrow(()->new ResourceNotFoundException("Cart","id", orderReqDto.getCartId()));

        Product product = productRepository.findById(cart.getProduct().getId())
                .orElseThrow(()->new ResourceNotFoundException("Product","id", cart.getProduct().getId()));

        Customer customer = customerRepository.findById(cart.getCustomer().getId())
                .orElseThrow(()->new ResourceNotFoundException("Customer","id", cart.getCustomer().getId()));



        Orders order = new Orders();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setCart(cart);
        order.setOrderDate(orderReqDto.getOrderDate());
        order.setPayment(orderReqDto.getPayment());
        order.setDeliveryDate(orderReqDto.getDeliveryDate());
        orderRepository.save(order);
        cartRepository.delete(cart);

        GenericResponse genericResponse = new GenericResponse<>();

        OrderResDto orderResDto = new OrderResDto();
        orderResDto.setOrderDate(order.getOrderDate());
        orderResDto.setPayment(order.getPayment());
        orderResDto.setDeliveryDate(order.getDeliveryDate());
        orderResDto.setProductId(order.getProduct().getId());
        orderResDto.setCustomerId(order.getCustomer().getId());
        orderResDto.setCartId(order.getCart().getId());

        genericResponse.setData(orderResDto);
        genericResponse.setMessage("Order successfully saved, cart deleted successfully");
        genericResponse.setSuccess(true);
        return genericResponse;
    }

    @Override
    public GenericResponse getAllOrders(Integer pageNo, Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        Page<Orders> ordersPage=orderRepository.findAll(pageRequest);
        List<Orders> ordersList=ordersPage.getContent();

        List<OrderResDto> orderResDtoList = ordersList
                .stream().map(orders ->modelMapper.map(orders,OrderResDto.class))
                .collect(Collectors.toList());
        GenericResponse genericResponse=new GenericResponse<>();
        genericResponse.setSuccess(true);
        genericResponse.setData(orderResDtoList);
        genericResponse.setMessage("Orders fetched successfully");
        return genericResponse;
    }

    @Override
    public GenericResponse getOrderByPayment(String payment) {

        List<Orders> ordersList = orderRepository.getOrderByPayment(payment);
        List<OrderResDto> orderResDtoList = ordersList
                .stream().map(orders -> modelMapper.map(orders,OrderResDto.class))
                .collect(Collectors.toList());

        GenericResponse genericResponse=new GenericResponse<>();
        genericResponse.setData(orderResDtoList);
        genericResponse.setSuccess(true);
        genericResponse.setMessage("Orders fetched successfully");
        return genericResponse;
    }
}
