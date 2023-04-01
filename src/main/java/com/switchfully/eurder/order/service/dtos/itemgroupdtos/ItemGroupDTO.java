package com.switchfully.eurder.order.service.dtos.itemgroupdtos;

import java.time.LocalDate;

public record ItemGroupDTO(String itemId, double price, int amountOrdered, LocalDate shippingDate) {


}
