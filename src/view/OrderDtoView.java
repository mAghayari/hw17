package view;

import model.OrderDto;

import java.util.List;

public class OrderDtoView {
    public void printOrderDtos(List<OrderDto> orderDtos){
        orderDtos.forEach(orderDto ->toString());
    }
}
