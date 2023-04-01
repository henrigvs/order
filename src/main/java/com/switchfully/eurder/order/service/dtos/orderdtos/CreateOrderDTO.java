package com.switchfully.eurder.order.service.dtos.orderdtos;

import com.switchfully.eurder.order.service.dtos.itemgroupdtos.ItemGroupDTO;
import java.util.List;

public record CreateOrderDTO (String customerId, List<ItemGroupDTO> itemGroupDTOList){
}
