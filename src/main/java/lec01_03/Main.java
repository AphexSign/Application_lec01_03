package lec01_03;

public class Main {
    public static void main(String[] args) {

//        RatNum scale=new RatNum(5,1);
//
//

        RatNum[] ratNum=new RatNum[2];
        ratNum[0]=new RatNum(1,1);
        ratNum[1]=new RatNum(1,1);



        RatNum[] ratNum2=new RatNum[2];
        ratNum2[0]=new RatNum(1,1);
        ratNum2[1]=new RatNum(3,1);


        RatPoly ratPoly=new RatPoly(ratNum);
        RatPoly ratPoly2=new RatPoly(ratNum2);

        System.out.println(ratPoly.toString());
        System.out.println(ratPoly2.toString());

        System.out.println(ratPoly.sub(ratPoly2));


    //    System.out.println(var1.toString());

//        System.out.println(ratPoly.toString());
//        System.out.println(ratPoly2.toString());







    }
}
