package com.switchfully.eurder.order.service.dtos.orderdtos;

import java.util.List;

public record OrderReportDTO(List<OrderDTO> allOrders, double totalPriceOfAllOrders) {
}
