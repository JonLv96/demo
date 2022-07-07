package pers.kksg.demo.beancopy;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pers.kksg.demo.entity.OrderDTO;
import pers.kksg.demo.entity.OrderPO;

import java.util.ArrayList;

class ConvertMapperTest {

    @Test
    void po2Dto() {
//        OrderPO orderPO = new OrderPO();
//        orderPO.setId(0);
//        orderPO.setOrderNumber("");
//        orderPO.setProId(new ArrayList<>());
//
//        ConvertMapper mapper = Mappers.getMapper(ConvertMapper.class);
//        OrderDTO orderDTO = (OrderDTO) mapper.po2Dto(orderPO);
//        System.out.println(JSON.toJSON(orderDTO));
//        System.out.println(JSON.toJSON(orderPO));

        System.out.println(OrderDTO.class);
    }
}