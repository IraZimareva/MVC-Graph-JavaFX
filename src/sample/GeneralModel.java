package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GeneralModel {
    private static ArrayList <PointModel> pointModels = new ArrayList<>();


    public static boolean addPoint (PointModel newPoint){
        for (PointModel point: pointModels){
            if (point.equals(newPoint)) return false;
        }//проверка на дубликат
        pointModels.add(newPoint);
        sortArray();
        return true;
    }

    public static void removePoint (PointModel removePoint){
        pointModels.remove(removePoint);
    }

    public static boolean editPoint (PointModel oldPoint, PointModel newPoint) {
        for (PointModel point : pointModels) {
            if (point.equals(oldPoint)) {
                point = newPoint;
                sortArray();
                return true;
            }//проверка на дубликат
        }
        return false;
    }

    public static void sortArray (){
        Collections.sort(pointModels, new Comparator<PointModel>() {
            @Override
            public int compare(PointModel p1, PointModel p2)
            {
                return  Double.compare(p1.getX(),p2.getX()); // -1 - less than, 1 - greater than, 0 - equal
            }
        });
    }

    public static ArrayList<PointModel> getPointModels() {
        return pointModels;
    }
}
