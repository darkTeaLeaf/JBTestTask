import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
    private Converter converter;

    @BeforeEach
    void init(){
        converter = new Converter();
    }

    @Test
    void test1(){
        String output = converter.convert("filter{(element>10)}%>%filter{(element<20)}");

        assertEquals("filter{((element>10)&(element<20))}%>%map{element}", output);
    }

    @Test
    void test2(){
        String output = converter.convert("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}");

        assertEquals("filter{((element+10)>10)}%>%map{((element+10)*(element+10))}", output);
    }

    @Test
    void test3(){
        String output = converter.convert("filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}");

        assertEquals("filter{((element>0)&(element<0))}%>%map{(element*element)}", output);
    }

    @Test
    void test4(){
        String output = converter.convert("filter{(element>0)}%>%map{(element-9)}%>%map{(element*element)}");

        assertEquals("filter{(element>0)}%>%map{((element-9)*(element-9))}", output);
    }

    @Test
    void test5(){
        String output = converter.convert("filter{((element>0)|(element=0))}%>%map{(element*7)}%>%map{element}");

        assertEquals("filter{((element>0)|(element=0))}%>%map{(element*7)}", output);
    }

    @Test
    void test6(){
        String output = converter.convert("filter{element}%>%filter{(element=0)}%>%map{(element*7)}");

        assertEquals("filter{(element=0)}%>%map{(element*7)}", output);
    }
}
