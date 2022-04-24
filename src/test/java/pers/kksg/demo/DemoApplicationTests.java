package pers.kksg.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        int i = 10;


    }

    public static void main(String[] args) {
        System.out.println(demo());;
    }

    public static int demo(){
        int i = 10;
        try {
            int c=  i/0;
            return --i;
        } catch (Exception e) {
            return --i;
        } finally {
            System.out.println("1111111");
            --i;
        }
    }
}
