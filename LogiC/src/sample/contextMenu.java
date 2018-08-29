package sample;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class contextMenu extends ContextMenu{
    private static TextField el11, el12, el21;
    private static helper helpers = new helper();

    /**
     * контекстне меню яке відкривається при даблъ-кліку на елемент
     * вміє видаляти елемент, змінювати тип та додавати номер елементу в поля
     * @param n номер елементу
     * @param e посилання на елемент
     */
    contextMenu(int n, element e){
        element e1 = e;

        Object o = this.getOwnerWindow();

        MenuItem item1 = new MenuItem("Додати елемент");
                item1.setOnAction(event -> {
                    el11.setText(String.valueOf(n));
                    el21.setText(String.valueOf(n));
                });

        MenuItem item2 = new MenuItem("Додати як другий елемент");
        item2.setOnAction(event -> {
            el12.setText(String.valueOf(n));
        });

        MenuItem item3 = new MenuItem("Видалити");
        item3.setOnAction(event -> {
            helpers.deletEl(e);
            e.setCoord();
            //helpers.creatable();
        });

        Menu elMenu = new Menu("Встановити елемент");

        MenuItem cItem1 = new MenuItem("Диз`юнкція");
        cItem1.setOnAction(event -> {
            e.setType("||");
            //helpers.creatable();
            e.setCoord();
        });

        MenuItem cItem2 = new MenuItem("Кон`юнкція");
        cItem2.setOnAction(event -> {
            e.setType("&");
            e.setCoord();
            //helpers.creatable();
        });

        MenuItem cItem3 = new MenuItem("Заперечення диз`юнкції");
        cItem3.setOnAction(event -> {
            e.setType("!|");
            e.setCoord();
            //helpers.creatable();
        });

        MenuItem cItem4 = new MenuItem("Заперечення кон`юнкції");
        cItem4.setOnAction(event -> {
            e.setType("!&");
            e.setCoord();
            //helpers.creatable();
        });

        MenuItem cItem5 = new MenuItem("Заперечення");
        cItem5.setOnAction(event -> {
            e.setType("!");
            e.setCoord();
            //helpers.creatable();
        });


        elMenu.getItems().addAll(cItem1, cItem2, cItem3, cItem4, cItem5);

        this.getItems().addAll(item1, item2, item3, elMenu);

    }

    /**
     * @param t1 поле1
     * @param t2 поле2
     * @param t3 поле3
     */
    public void setFields(TextField t1, TextField t2, TextField t3){
        el12 = t2;
        el21 = t3;
        el11 = t1;
    }

}
