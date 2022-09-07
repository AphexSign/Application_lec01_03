package lec01_03;

import org.junit.Test;

import static org.junit.Assert.*;

public class RatPolyTest {

    //Специальный метод для проверки парсинга данных ValueOf
    private void polyStringTest(String str) {
        assertEquals(str, RatPoly.valueOf(str).toString());
    }

    @Test
    public void testParsing() {
        polyStringTest("x^2+x");
        polyStringTest("x^3+2*x");
        polyStringTest("-x^2");
        polyStringTest("-x^2+1");
        polyStringTest("-x^2+x");
        polyStringTest("1/2");
        polyStringTest("1/2*x");
        polyStringTest("x+1/3");
        polyStringTest("1/2*x+1/3");
        polyStringTest("1/2*x+3/2");
        polyStringTest("1/2*x^10+3/2");
        polyStringTest("1/2*x^10+3/2*x^2+1");
    }

    //Тесты для конструктора c двумя аргументами
    @Test
    public void testTwoArgConstr() {
        assertEquals(new RatPoly(3, 2).toString(), "3*x^2");
        assertEquals(new RatPoly(0, 0).toString(), "0");
        assertEquals(new RatPoly(1, 0).toString(), "1");
        assertEquals(new RatPoly(1, 1).toString(), "x");
        assertEquals(new RatPoly(1, 2).toString(), "x^2");
        assertEquals(new RatPoly(2, 2).toString(), "2*x^2");
        assertEquals(new RatPoly(2, 3).toString(), "2*x^3");
        assertEquals(new RatPoly(-2, 3).toString(), "-2*x^3");
        assertEquals(new RatPoly(-1, 1).toString(), "-x");
        assertEquals(new RatPoly(-1, 3).toString(), "-x^3");
      //  assertEquals(new RatPoly(-1, 100000000).toString(), "-x^100000000");
        //Не протестировано возможность сложения таких аргументов
        //Их разность, умножение и деление
    }

    //Конструктор с нулевыми аргументами
    @Test
    public void testZeroArgConst() {
        assertEquals(new RatPoly(0, 0).toString(), "0");
    }

    //Проверяет строковое представление
    @Test
    public void testNaN() {
        //Является неопределенным
        assertTrue("not NaN",RatPoly.valueOf("NaN").isNaN());
        assertTrue("not NaN",RatPoly.valueOf("1/0*x^2").isNaN());
        //Не NaN
        assertFalse(RatPoly.valueOf("1").isNaN());
        assertFalse(RatPoly.valueOf("1/2").isNaN());
        assertFalse(RatPoly.valueOf("x+1").isNaN());
        assertFalse(RatPoly.valueOf("x^2+x+1").isNaN());
    }

    //Возвращает максимальную степень
    @Test
    public void degree() {
        assertEquals(RatPoly.valueOf("x^3+x^2+x+5").degree(), 3);
        assertEquals(RatPoly.valueOf("x^3+x+5").degree(), 3);
        assertEquals(RatPoly.valueOf("x^100+55").degree(), 100);
        assertEquals(RatPoly.valueOf("x+5").degree(), 1);
        assertEquals(RatPoly.valueOf("5").degree(), 0);
    }

    @Test
    public void getCoeff() {
        assertEquals(RatPoly.valueOf("5*x^2+20*x+9").getCoeff(2),new RatNum(5));
        assertEquals(RatPoly.valueOf("5*x^2+20/3*x+9").getCoeff(1),new RatNum(20,3));
        assertEquals(RatPoly.valueOf("5*x^2+20/3*x+9").getCoeff(0),new RatNum(9,1));

    }


    //Проверка инверсии знаков в полиноме
    @Test
    public void negate() {
        assertEquals(RatPoly.valueOf("x^2+x").negate().toString(), "-x^2-x");
        assertEquals(RatPoly.valueOf("-x").negate().toString(), "x");
        assertNotEquals(RatPoly.valueOf("-x").negate().toString(), "-x");
    }

    @Test
    public void add() {
        assertEquals(RatPoly.valueOf("x^2+x").add(RatPoly.valueOf("x^2+x")).toString(), "2*x^2+2*x");
        assertEquals(RatPoly.valueOf("x^2+x+2").add(RatPoly.valueOf("x^2+x")).toString(), "2*x^2+2*x+2");
        assertEquals(RatPoly.valueOf("x^2+x+2").add(RatPoly.valueOf("x^5+x")).toString(), "x^5+x^2+2*x+2");
        assertEquals(RatPoly.valueOf("x^2+x+2").add(RatPoly.valueOf("5*x^5")).toString(), "5*x^5+x^2+x+2");
    }

    @Test
    public void sub() {
        assertEquals(RatPoly.valueOf("x+1").sub(RatPoly.valueOf("3*x+1")).toString(), "-2*x");
        assertEquals(RatPoly.valueOf("2*x").sub(RatPoly.valueOf("x+1")).toString(), "x-1");
        assertEquals(RatPoly.valueOf("x^2").sub(RatPoly.valueOf("x^3")).toString(), "-x^3+x^2");
    }

    //Разница между полиномами, при одинаковом коэффициенте
    @Test
    public void subOneCoeff() {
        assertEquals(RatPoly.valueOf("x+1").sub(RatPoly.valueOf("x+5")).toString(), "-5");

    }



    @Test
    public void mul() {
        assertEquals(RatPoly.valueOf("x+1").mul(RatPoly.valueOf("x+1")).toString(), "x^2+2*x+1");
    }

    @Test
    public void div() {
        assertEquals(RatPoly.valueOf("x^2").div(RatPoly.valueOf("x")),"x");

    }

    @Test
    public void eval() {
        assertEquals(RatPoly.valueOf("x^2+x+5").eval(1),7.00,0.0001);
        assertEquals(RatPoly.valueOf("3*x^2+x+5").eval(2),19.00,0.0001);
        assertEquals(RatPoly.valueOf("x^3+x^2+100").eval(5),250,0.0001);
        assertEquals(new RatPoly(1,3).eval(5),125,0.0001);
        assertEquals(new RatPoly(0,0).eval(5),0,0.0001);
    }

    @Test
    public void differentiate() {
        //Производная
        assertEquals(RatPoly.valueOf("3*x^2+5").differentiate(),"6x");


    }

    @Test
    public void antiDifferentiate() {
        //Первообразная
        // c=1;  aD(2x)-> 2*x^2/2+c = x^2+1
        assertEquals(RatPoly.valueOf("2x").antiDifferentiate(new RatNum(1,1)).toString(),"x^2+1");

    }

    @Test
    public void integrate() {
        //Взятие первообразной и применение eval - по нужным пределам. Предел высокий - предел низкий
        assertEquals(RatPoly.valueOf("2x").integrate(1.0,3.0),8,0.0001);


    }

}