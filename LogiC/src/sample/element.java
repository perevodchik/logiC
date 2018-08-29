package sample;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Polyline;

import java.util.Arrays;

public class element extends Label{
    private double oldX;
    private double oldY;
    private String c1 = "", c2 = "";
    private int id;
    private String img = "imgs/some.png";
    private int[] b = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    private int[] b1 = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    private int[] b2 = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
    private calculated myCalc = new calculated();
    private element[] els = new element[]{null, null};
    private static helper helpers = new helper();
    private String type = "empty";
    private static calculated calc = new calculated();
    private element pEl = null;
    private Polyline[] lone = new Polyline[]{new Polyline(0, 0, 0, 0, 0, 0, 0, 0), new Polyline(0, 0, 0, 0, 0, 0, 0, 0)};

    /**
     * конструктор елементів
     * @param id айді елементу
     * @param x координата х (не ікс а х!)
     * @param y координата у
     */
    element(int id, double x, double y){
        this.id = id;
        this.setPrefSize(50, 50);
        this.setLayoutY(y);
        this.setLayoutX(x);
        this.setGraphic(new javafx.scene.image.ImageView(new Image(getClass().getResourceAsStream(img))));

        this.setOnMousePressed(e -> {

            helpers.setN(id);

            if(e.getClickCount() == 2){
            contextMenu cm = new contextMenu(helpers.getN(), this);
            cm.show(this, x+400+getTranslateX(), y+200+getTranslateY());
            }

            oldX = this.getTranslateX()-e.getSceneX();
            oldY = this.getTranslateY()-e.getSceneY();
        });

        this.setOnMouseDragged(e -> {
            this.setTranslateX(oldX+e.getSceneX());
            this.setTranslateY(oldY+e.getSceneY());
            setCoord();
            if(pEl != null){
            pEl.setCoord();}
        });
    }

    /**
     * встановлює координати Полілайнів елементу якщо він з'єжнаний зі змінною чи іншим елементом
     * при йог опереміщенні мишкою
     */
    public void setCoord(){
        int s = 0, sc = 0, ap = 0;
        String c = "";
        if(type.equals("!")){
            sc = 0;
            ap = 15;
            lone[1].getPoints().remove(0, 8);
            lone[1].getPoints().addAll(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        } else {
            sc = 1;
            ap = 0;
        }
            for(int i = 0; i<=sc; i++) {
                if (i == 0) {
                    s = 10;
                    c = c1;
                } else if (i == 1) {
                    s = 40;
                    c = c2;
                }
                double x1 = 0, x2 = 0, x3 = 0, y1 = 0, y3 = 0;
                x3 = this.getLayoutX() + this.getTranslateX();
                y3 = this.getLayoutY() + this.getTranslateY() + s + ap;
                if (els[i] != null) {
                    x1 = els[i].getLayoutX() + els[i].getTranslateX() + 50;
                    y1 = els[i].getLayoutY() + els[i].getTranslateY() + 25;
                    x2 = x1 + (x3 - x1) / 2;
                    lone[i].getPoints().addAll(x1, y1, x2, y1, x2, y3, x3, y3);
                    lone[i].getPoints().remove(0, 8);
                } else if (!c.equals("")) {
                    switch (c) {
                        case "a":
                            x1 = 10;
                            break;
                        case "b":
                            x1 = 20;
                            break;
                        case "!a":
                            x1 = 30;
                            break;
                        case "!b":
                            x1 = 42;
                            break;
                    }
                    lone[i].getPoints().addAll(x1, y3, x1, y3, x1, y3, x3, y3);
                    lone[i].getPoints().remove(0, 8);
                }
            }
    }

    /**
     * повернення вихідного виразу елементу для таблиці, або інший елементів для створення іх виразів
     * @return стрінг вираз елементу
     */
    public String getViraz(){
        String s1 = "", s2 = "";
        if(!type.equals("!")){
            if(els[0]!=null){
                s1 = "(" + els[0].getViraz() + ")";
            }else if(!c1.equals("")){
                s1 = c1;
            } else s1 = "";

        if(els[1]!=null){
            s2 = "(" + els[1].getViraz() + ")";
        } else if(!c2.equals("")){
            s2 = c2;
        } else {
            s2 = "";
        }
        } else {
            if(els[0]!=null){
                s2 = "(" + els[0].getViraz() + ")";
            } else if(!c1.equals("")){
                s2 = "(" + c1 + ")";
            } else {
                s1 = "";
                s2 = "";}
        }


        return s1 + String.valueOf(type) + s2;
    }

    /**
     * для з'єднання зі змінною
     * @param c змінна
     * @param v вхід
     */
    public void setVar(String c, int v){
        if(!type.equals("!")){
            if(v == 1){
                this.c1 = c;
            } else if(v == 2){
                this.c2 = c;
            }
        } else {
            this.c1 = c;
        }
    }

    /**
     * для з'єднання з іншим елементом
     * @param e посілання на елемент
     * @param v вхід
     */
    public void setE(element e, int v){
        if(!type.equals("!")){
            if(v == 1){
                this.els[0] = e;
            } else if(v == 2){
                this.els[1] = e;
            }
        } else {
            els[0] = e;
        }
    }

    /**
     * встановлення типу поточного елементу
     * @see contextMenu
     * @param type тип елементу
     */
    public void setType(String type){
        switch (type) {
            case "||":
                this.setIco("imgs/diz.png");
                break;
            case "&":
                this.setIco("imgs/kon.png");
                break;
            case "!|":
                this.setIco("imgs/inv_diz.png");
                break;
            case "!&":
                this.setIco("imgs/inv_kon.png");
                break;
            case "!":
                this.setIco("imgs/not.png");
                break;
        }
        this.type = type;
    }

    /**
     * встановлення масиву значень елементу\змінної
     * потім обчислюємо 2 масиви в calculated
     * і вихідний масив записується в таблицю істинності
     * @param b масив
     * @param v вхід\номер масиву
     */
    public void setB(int[] b, int v){
        if(!type.equals("!")) {
            if (v == 1) {
                this.b1 = b;
            } else if (v == 2) {
                this.b2 = b;
            }
        } else {
            b1 = b;
            b2 = null;
        }
        this.b = myCalc.calc(b1, b2, String.valueOf(type));
    }

    /**
     * повернення готового масиву
     * @return int масив значень елементу
     */
    public int[] getB(){
        if(els[0] != null){
            b1 = els[0].getB();
        }
        if(els[1] != null){
            b2 = els[1].getB();
        }
        return calc.calc(b1, b2, type);
    }

    /**
     * встановлення іконки елементу, при зміні типу
     * @see contextMenu
     * @param imgs приймає параметр зі шляхом до зоббраження
     */
    public void setIco(String imgs){
        this.setGraphic(new javafx.scene.image.ImageView(new Image(getClass().getResourceAsStream(imgs))));
    }

    /**
     * повертає полілайн цього елементу
     * @return полілайн
     */
    public Polyline[] getLineA(){
        return lone;
    }

    /**
     * повертає масив елементів, якщо поточний елеменет з'єднаний хочаб з одним
     * @return масив елементів
     */
    public element[] getEls(){
        return els;
    }

    /**
     * встановлює в масив елементів елемент при з'єднанні
     * @param en позиція в масиві
     * @param e посилання на елемент
     */
    public void setEls (int en, element e){
        els[en] = e;
    }

    /**
     * повернення айді потомного елементу
     * @return айді елементу
     */
    public int getIdEl(){
        return id;
    }

    /**
     * повертає тип даного елементу
     * @return тип елменту
     */
    public String getType(){
        return type;
    }

    /**
     * якщо за входом 1 з'єднаний зі змінною
     * повертає її
     * @return змінна1
     */
    public String getC1(){
        return c1;
    }
    /**
     * якщо за входом 2 з'єднаний зі змінною
     * повертає її
     * @return змінна2
     */
    public String getC2(){
        return c2;
    }

    /**
     * становлює елемент з яким буде з'єднаний
     * @param pEl наступний елемент
     */
    public void setNextEl(element pEl){
        this.pEl = pEl;
    }

    /**
     * повертає посилання на елемент з яким зє'днаний
     * @return pEl наступний елемент
     */
    public element getNextEl(){
        return pEl;
    }
}