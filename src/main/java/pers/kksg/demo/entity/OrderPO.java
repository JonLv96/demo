package pers.kksg.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * OrderPO
 *
 * @Author Jonlv
 * @Description TODO
 * @Date 2022/5/31 16:18
 */
@Data
@NoArgsConstructor
public class OrderPO {
    Integer id;
    Integer orderNumber;
    List<String> proId;

    public OrderPO(Integer id, Integer orderNumber) {
        this.id = id;
        this.orderNumber = orderNumber;
    }
}



