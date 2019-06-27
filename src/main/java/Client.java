/**
 * @Author: ly
 * @Date: 2019/6/27 10:19
 */
public class Client {

    public static void buy(){
        System.out.println("----------["+Thread.currentThread().getName()+"]开始购买-----------");
        //获取商品数量
        int currentNumber=Product.getNumber();

        if(currentNumber==0){
            System.out.println("商品已被抢空！！");
        }else{
            System.out.println("当前商品数量:"+currentNumber);

            //购买后数量-1
            currentNumber--;
            Product.setNumber(currentNumber);


            //为了方便观察，延迟三秒
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("---------["+Thread.currentThread().getName()+"]购买结束-------");
        }
    }

}
