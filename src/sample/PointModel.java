package sample;


import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


public class PointModel {
        //private DoubleProperty x;
        private double x;
        private double y;

        public PointModel(double x) {
            //this.x = new SimpleDoubleProperty(x);
            this.x = x;
            func(x);
        }

        public void func(double argX) {
            this.y = 5 - argX * argX; //y=5-x^2
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
            this.func(x);
        }

        public double getY() {
            return y;
        }
    }
