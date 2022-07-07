package pers.kksg.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * OrderDTO
 *
 * @Author Jonlv
 * @Description TODO
 * @Date 2022/5/31 16:19
 */
@Data
public class OrderDTO {
    int id;
    String orderNumber;
    List<String> proId;
}
