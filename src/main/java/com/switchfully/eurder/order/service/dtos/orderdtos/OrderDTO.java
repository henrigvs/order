package com.switchfully.eurder.order.service.dtos.orderdtos;

import com.switchfully.eurder.order.service.dtos.itemgroupdtos.ItemGroupDTO;
import java.time.LocalDate;
import java.util.List;

public record OrderDTO (String orderId, String customerId, List<ItemGroupDTO> itemGroupDTOList, double totalPrice, LocalDate orderDate){
}
