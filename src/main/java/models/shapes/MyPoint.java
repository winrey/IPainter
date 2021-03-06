package models.shapes;

import com.google.gson.JsonObject;
import models.interfaces.IOperatable;
import models.interfaces.ISerialized;

import java.awt.*;

import static java.lang.Math.*;

public class MyPoint implements IOperatable, ISerialized {
    public double x, y;

    public MyPoint(double x, double y){
        this.x = x;
        this.y = y;
    }
    public MyPoint(Point p){
        this(p.x, p.y);
    }
    public MyPoint(MyPoint p){
        this(p.x, p.y);
    }
    public void translate(double dx, double dy){
        x += dx;
        y += dy;
    }
    public void rotate(double alpha, double cx, double cy){
        // 移到原点
        this.translate(-cx, -cy);
        this.rotate(alpha);
        // 移回原位
        this.translate(cx, cy);
    }
    public void rotate(double alpha){
        double rad = alpha / 360 * Math.PI;
        double nx = x * cos(rad) + y * sin(rad);
        double ny = y = -x * sin(rad) + y * cos(rad);
        x = nx;
        y = ny;
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "MyPoint");
        json.addProperty("x", x);
        json.addProperty("y", y);
        return json;
    }

    public static MyPoint parseFromJsonFactory(JsonObject json) {
        if (!json.get("type").getAsString().equals("MyPoint")){
//            throw new JsonSyntaxException("没有Point标签！");
            return null;
        }
        double x = json.get("x").getAsDouble();
        double y = json.get("y").getAsDouble();
        return new MyPoint(x, y);
    }

    public double getDistance(MyPoint point){
        double x2 = Math.pow(this.x - point.x, 2);
        double y2 = Math.pow(this.y - point.y, 2);
        return Math.sqrt(x2 + y2);
    }

    public Point toPoint(){
        return new Point((int)Math.round(this.x), (int)Math.round(this.y));
    }

}
