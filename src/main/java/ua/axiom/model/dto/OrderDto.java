package ua.axiom.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.axiom.model.objects.Order;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDto {
    public long id;
    public String clientUsername;
    public String driverUsername;
    public long clientId;
    public long driverId;

    public float price;
    public Date date;
    public String destination;
    public String departure;


    public static List<OrderDto> factory(List<Order> orders) {
        return orders
                .stream()
                .map(OrderDto::factory)
                .collect(Collectors.toList());
    }

    public static OrderDto factory(Order order) {
        OrderDto dto = new OrderDto();

        try {
            dto.clientId = order.getClient().getId();
            dto.clientUsername = order.getClient().getUsername();
        } catch (NullPointerException npe) {
            dto.clientId = -1;
            dto.clientUsername = "";

        }

        try {
            dto.driverId = order.getDriver().getId();
            dto.driverUsername = order.getDriver().getUsername();
        } catch (NullPointerException npe) {
            dto.driverId = -1;
            dto.driverUsername = "";
        }

        dto.id = order.getId();
        dto.date = order.getDate();
        dto.departure = order.getDeparture();
        dto.destination = order.getDestination();
        dto.price = order.getPrice().floatValue();

        return dto;
    }
}
