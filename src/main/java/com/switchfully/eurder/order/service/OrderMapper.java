package com.switchfully.eurder.order.service;

import com.switchfully.eurder.order.domain.Order;
import com.switchfully.eurder.order.service.dtos.orderdtos.CreateOrderDTO;
import com.switchfully.eurder.order.service.dtos.orderdtos.OrderDTO;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    ItemGroupMapper itemGroupMapper = new ItemGroupMapper();

    public OrderDTO toDTO(Order order){
        return new OrderDTO(
                order.getOrderId(),
                order.getCustomerId(),
                itemGroupMapper.toDTOList(order.getItemGroups()),
                order.getTotalPrice(),
                order.getOrderDate());
    }

    public List<OrderDTO> toDTOList (List<Order> orderList){
        return orderList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Order toEntity(CreateOrderDTO createOrderDTO){
        return new Order(
                createOrderDTO.customerId(),
                itemGroupMapper.toEntityList(createOrderDTO.itemGroupDTOList()));
    }
}
