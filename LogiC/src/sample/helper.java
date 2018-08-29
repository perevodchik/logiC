package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class helper {
    static int type = 0;
    static int n = -1, c = 0;
    private static Pane pane;
    private static ArrayList<element> elList = new ArrayList<>();
    private TableView myTable;

    private Boolean isStarted = true;

    private ArrayList<TableColumn> ttc = new ArrayList<>();

    helper(){}

    /**
     * при втриманні Cntrl встановлюється знач. 1
     * @see Main
     * @param t тип елементу
     */
    public void setType(int t){
        this.type = t;
    }

    /**
     * повернення значення при спробі додати елемент на панель.
     * Якщо 1 = елемент додавати можна, якщо 0 = не можна. genius
     * @see Main
     * @return int
     */
    public int getType(){
        return type;
    }

    /**
     * @see contextMenu
     * @param n id елементу
     */
    public void setN(int n){
        this.n = n;
    }

    /**
     * передає в contextMenu id елементу, яке потім додається в деякі поля
     * @see contextMenu
     * @return id елементу
     */
    public int getN(){
        return n;
    }

    /**
     * колекція елементів на панелі, з якою працюємо
     * @return колекція всіх елементів на панелі
     */
    public ArrayList<element> getElList(){
        return elList;
    }

    /**
     * при створенні схеми з файлу сюди передається колекція новостворених елементів, або при видаленні передається порожня
     * @param elList колекція елементів панелі
     */
    public void setElList(ArrayList<element> elList){
        this.elList = elList;
    }

    /**
     * видаляємо елемент з панелі та колекції
     * @param n елемент
     */
    public void deletEl(element n){
        if(n.getEls()[0]!=null)
            if(n.getEls()[0].equals(n)){
                n.setEls(0, null);
        } else if(n.getEls()[1]!=null){
            if(n.getEls()[1].equals(n)){
                n.setEls(1, null);
            }
        }

        elList.remove(n);
        pane.getChildren().remove(n.getLineA()[0]);
        pane.getChildren().remove(n.getLineA()[1]);
        pane.getChildren().remove(n);
    }

    /**
     * функція яка додає на панель новий елемент, якщо зажата клавіша cntrl
     * @param x ккодината х
     * @param y координата у
     */
    public void addOnPanel(double x, double y){
        if (type == 1) {

            elList.add(new element(c, x, y));
            pane.getChildren().add(elList.get(c));
            Polyline[] pg = elList.get(c).getLineA();
            pane.getChildren().addAll(pg[0], pg[1]);

            c++;
        }
    }

    /**
     * функція яка з'єднує елемент зі змінною
     * перевіряє певні поля регулярними виразами, для того щоб там не було написано зайвого
     * і не було помилок
     * @param el номер елементу
     * @param var текстове значення змінної
     * @param vhid вхід по якому буде зєднано
     */
    public void setWithVar(int el, int vhid, String var){
        int[] vA = new int[]{0, 0, 0, 0, 1, 1, 1, 1};
        int[] vB = new int[]{0, 0, 1, 1, 0, 0, 1, 1};
        int[] vNA = new int[]{1, 1, 1, 1, 0, 0, 0, 0};
        int[] vNB = new int[]{1, 1, 0, 0, 1, 1, 0, 0};

        if(!elList.isEmpty()) {

            if(el <elList.size() && (vhid == 1 || vhid == 2) && (var.equals("a") || var.equals("b") || var.equals("!a") || var.equals("!b"))){

            String v = "";
            int[] mb = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

            switch (var) {
                case "a":
                    v = "a";
                    mb = vA;
                    break;
                case "b":
                    v = "b";
                    mb = vB;
                    break;
                case "!a":
                    v = "!a";
                    mb = vNA;
                    break;
                case "!b":
                    v = "!b";
                    mb = vNB;
                    break;
            }

            elList.get(el).setVar(v, vhid);
            elList.get(el).setB(mb, vhid);
            elList.get(el).setE(null, vhid);
            elList.get(el).setCoord();
            }
        }
    }

    /**
     * функцція яка з'єжнує елемент1 та елемент2, схожа на функцію clic1
     * також перевіряє поля регулярними виразами на наявність зайвих символів
     * @param e1 перший елемент
     * @param e2 другий елемент
     * @param vhid вхід по якому буде зєднано
     */
    public void setWithEl(int e1, int e2, int vhid){
        if((e1 < elList.size() && e2 <elList.size()) && (vhid == 1 || vhid == 2) && (e1 != e2)){
            if(!elList.isEmpty() & elList.size()>1){

                elList.get(e1).setB(elList.get(e2).getB(), vhid);
                elList.get(e1).setVar("", vhid);
                elList.get(e1).setE(elList.get(e2), vhid);
                elList.get(e1).setCoord();
                elList.get(e2).setNextEl(elList.get(e1));
            }
        }
    }

    /**
     * типу очистка всієї панелі
     */
    public void clean(){
        for(int i = elList.size()-1; i>=0; i--){
            Polyline[] p = elList.get(i).getLineA();
            pane.getChildren().remove(p[0]);
            pane.getChildren().remove(p[1]);
            pane.getChildren().remove(elList.get(i));
        }
        elList.removeAll(elList);
        c = 0;
    }

    /**
     * створення таблиці істинності.
     * тут ми заносимо в масив всі значення вихідного масиву елементів
     * додаємо нові колонки таблиці з вихідних виразів елементів
     * та заносимо туди масив
     */
    public void creatable(){
        ArrayList<element> eList = elList;
        String[] dataList = new String[]{"0,0,1,1", "0,0,1,1",
                "0,1,1,0", "0,1,1,0",
                "1,0,0,1", "1,0,0,1",
                "1,1,0,0", "1,1,0,0"};

        ArrayList<String> tl = new ArrayList<>();

        if(!isStarted){
            myTable.getColumns().remove(0, ttc.size());
        }

        tl.add("a");
        tl.add("b");
        tl.add("!a");
        tl.add("!b");

        for(int i = 0; i<=eList.size()-1; i++) {
            int[] b = eList.get(i).getB();
            for (int j = 0; j <= 7; j++) {
                dataList[j] += "," + String.valueOf(b[j]);
            }
        }

        for(int i = 0; i<=eList.size()-1; i++){
            tl.add(eList.get(i).getViraz());
        }

        ObservableList<String[]> data = FXCollections.observableArrayList();

        for(int i = 0; i<=7; i++){
            data.add(dataList[i].split(","));
        }

        ttc.removeAll(ttc);

        for(int i = 0; i<=tl.size()-1; i++){
            TableColumn tc = new TableColumn((tl.get(i)));
            final int o = i;
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {

                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[o]));
                }
            });
            tc.setPrefWidth(80);
            ttc.add(tc);
        }

        for(TableColumn t : ttc){
            myTable.getColumns().add(t);
        }
        myTable.setItems(data);
        isStarted = false;
    }

    /**
     * створення таблиці з файлу. При створенні таблиці попередня видаляється
     * @throws FileNotFoundException якщо файлу не було знайдено
     */
    public void loadFromFile() throws FileNotFoundException {
        Pattern chisloFind = Pattern.compile("\\d");
        Pattern varFind = Pattern.compile("!?[a-bA-B]");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectFile = fileChooser.showOpenDialog(null);
        String loadata = "";
        FileReader fR = new FileReader(selectFile);
        Scanner thisScanner = new Scanner(fR);
        String[] tmpArray = new String[]{"", "", "", "", "", "", ""};
        ArrayList<element> tmpArr = elList;

        clean();

        if (!tmpArr.isEmpty()) {
            for (element er : tmpArr){
                pane.getChildren().remove(er);
            }
            elList.removeAll(tmpArr);
            tmpArr.removeAll(tmpArr);
        }

        while(thisScanner.hasNextLine()){
            loadata += thisScanner.nextLine();
        }

        System.out.println(loadata);

        String[] dataArray = loadata.split("N");
        for(int i = 0; i<=dataArray.length-1; i++) {
            tmpArray = dataArray[i].split(",");
            System.out.println(Arrays.toString(tmpArray));

            tmpArr.add(new element(Integer.valueOf(tmpArray[0]), Double.parseDouble(tmpArray[2]), Double.parseDouble(tmpArray[3])));
            tmpArr.get(i).setType(tmpArray[1]);
        }

        for(int i = 0; i<=dataArray.length-1; i++) {
            System.out.println("!!!!!!!!!!!!!" + i);
            tmpArray = dataArray[i].split(",");
            for (int p = 0; p <= 1; p++) {
                if (!tmpArray[4 + p].equals("-")) {
                    Matcher matChislo = chisloFind.matcher(tmpArray[4 + p]);
                    Matcher matVar = varFind.matcher(tmpArray[4 + p]);
                    if (matChislo.find()) {
                        System.out.println(matChislo.group());
                        setWithEl(Integer.valueOf(tmpArray[0]), Integer.valueOf(tmpArray[4 + p]), 1 + p);
                    } else if (matVar.find()) {
                        System.out.println(matVar.group());
                        setWithVar(Integer.valueOf(tmpArray[0]), 1 + p, tmpArray[4 + p]);
                    }
                }
            }
            if (!tmpArray[6].equals("-")) {
                tmpArr.get(i).setNextEl(tmpArr.get(Integer.valueOf(tmpArray[6])));
            }
        }

        for(element ttmp : tmpArr) {
            pane.getChildren().add(ttmp);
            pane.getChildren().addAll(ttmp.getLineA());
        }

        elList = tmpArr;
        c = tmpArr.size();
        System.out.println(c);}

    /**
     * встановлює посилання на панель в змінну
     * @param pane Pane поточна панель
     * @param myTable таблиця для передачі посилання на неї в цей клас
     */
    public void setI(Pane pane, TableView myTable){
        this.pane = pane;
        this.myTable = myTable;
    }

}
