package sample;

public class calculated {

    calculated(){
    }


    /**
     * функція яка обчислює два масиви при з`єднанні елементу зі змінною чи другим елементом
     * дані з вихідного масиву будуть занесені в таблицю істинності
     * @param a1 масив першого елементу
     * @param a2 масив другого елементу
     * @param type тип елементу
     * @return повернуння масиву типу int з
     */
    public int[] calc(int[] a1, int[] a2, String type) {
        int[] returnedArr = new int[8];

        for (int i = 0; i <= 7; i++){
            if (type.equals("||")) {//diz
                if(a1[i] == 1 || a2[i] == 1){
                    returnedArr[i] = 1;
                }
                else returnedArr[i] = 0;
            }
            else if (type.equals("&")) {//kon
                if(a1[i] == 1 && a2[i] == 1){
                    returnedArr[i] = 1;
                }
                else returnedArr[i] = 0;
            }
            else if (type.equals("!&")) {//!kon
                if(a1[i] == 1 && a2[i] == 1){
                    returnedArr[i] = 0;
                }
                else returnedArr[i] = 1;
            }
            else if (type.equals("!|")) {//!diz
                if(a1[i] == 0 && a2[i] == 0){
                    returnedArr[i] = 1;
                }
                else returnedArr[i] = 0;
            }
            else if (type.equals("!")) {//inv
                if(a1[i] == 0){
                    returnedArr[i] = 1;
                }
                else if(a1[i] == 1){
                    returnedArr[i] = 0;
                }
            }
        }
        return  returnedArr;
    }

}
