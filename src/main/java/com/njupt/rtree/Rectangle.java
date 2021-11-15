package com.njupt.rtree;


/** 

 * @ClassName Rectangle  
 * @Description  
 */  
public class Rectangle implements Cloneable  
{  
    private Point low;  
    private Point high;  
      
    public Rectangle(Point p1, Point p2)  
    {  
        if(p1 == null || p2 == null)  
        {  
            throw new IllegalArgumentException("Points cannot be null.");  
        }  
        if(p1.getDimension() != p2.getDimension())  
        {  
            throw new IllegalArgumentException("Points must be of same dimension.");  
        }  

        for(int i = 0; i < p1.getDimension(); i ++)  
        {  
            if(p1.getDoubleCoordinate(i) > p2.getDoubleCoordinate(i))  
            {  
                throw new IllegalArgumentException("");
            }  
        }  
        low = (Point) p1.clone();  
        high = (Point) p2.clone();  
    }  
      
    /** 

     * @return Point 
     */  
    public Point getLow()   
    {  
        return (Point) low.clone();  
    }  
  
    /** 

     * @return Point 
     */  
    public Point getHigh()   
    {  
        return high;  
    }  
  
    /** 
     * @param rectangle 
     * @return
     */  
    public Rectangle getUnionRectangle(Rectangle rectangle)  
    {  
        if(rectangle == null)  
            throw new IllegalArgumentException("Rectangle cannot be null.");  
          
        if(rectangle.getDimension() != getDimension())  
        {  
            throw new IllegalArgumentException("Rectangle must be of same dimension.");  
        }  
          
        double[] min = new double[getDimension()];  
        double[] max = new double[getDimension()];  
          
        for(int i = 0; i < getDimension(); i ++)  
        {  
            min[i] = Math.min(low.getDoubleCoordinate(i), rectangle.low.getDoubleCoordinate(i));  
            max[i] = Math.max(high.getDoubleCoordinate(i), rectangle.high.getDoubleCoordinate(i));  
        }  
          
        return new Rectangle(new Point(min), new Point(max));  
    }  
      
    /**
     */  
    public double getArea()  
    {  
    	double area = 1;  
        for(int i = 0; i < getDimension(); i ++)  
        {  
            area *= high.getDoubleCoordinate(i) - low.getDoubleCoordinate(i);  
        }  
          
        return area;  
    }  
      
    /** 
     * @param rectangles
     */  
    public static Rectangle getUnionRectangle(Rectangle[] rectangles)  
    {  
        if(rectangles == null || rectangles.length == 0)  
            throw new IllegalArgumentException("Rectangle array is empty.");  
          
        Rectangle r0 = (Rectangle) rectangles[0].clone();  
        for(int i = 1; i < rectangles.length; i ++)  
        {  
            r0 = r0.getUnionRectangle(rectangles[i]);  
        }  
          
        return r0;  
    }  
      
    @Override  
    protected Object clone()  
    {  
        Point p1 = (Point) low.clone();  
        Point p2 = (Point) high.clone();  
        return new Rectangle(p1, p2);  
    }  
      
    @Override  
    public String toString()   
    {  
        return "Rectangle Low:" + low + " High:" + high;  
    }  
      
    public static void main(String[] args)   
    {  
    	double[] f1 = {1.3f,2.4f};  
    	double[] f2 = {3.4f,4.5f};  
        Point p1 = new Point(f1);  
        Point p2 = new Point(f2);  
        Rectangle rectangle = new Rectangle(p1, p2);  
        System.out.println(rectangle);  
//      Point point = rectangle.getHigh();  
//      point = p1;  
//      System.out.println(rectangle);  
          
        double[] f_1 = {0f,0f};  
        double[] f_2 = {-2f,2f};  
        double[] f_3 = {3f,3f};  
        double[] f_4 = {2.5f,2.5f};  
        double[] f_5 = {1.5f,1.5f};  
        p1 = new Point(f_1);  
        p2 = new Point(f_2);  
        Point p3 = new Point(f_3);  
        Point p4 = new Point(f_4);  
        Point p5 = new Point(f_5);  
        Rectangle re1 = new Rectangle(p1, p2);  
        Rectangle re2 = new Rectangle(p2, p3);  
        Rectangle re3 = new Rectangle(p4, p3);  
//      Rectangle re4 = new Rectangle(p3, p4);  
        Rectangle re5 = new Rectangle(p5, p4);  
        System.out.println(re1.isIntersection(re2));  
        System.out.println(re1.isIntersection(re3));  
        System.out.println(re1.intersectingArea(re2));  
        System.out.println(re1.intersectingArea(re5));  
    }  
  
    /**
     * @param rectangle Rectangle 
     * @return float 
     */  
    public float intersectingArea(Rectangle rectangle)   
    {  
        if(! isIntersection(rectangle))  
        {  
            return 0;  
        }  
          
        float ret = 1;  
        for(int i = 0; i < rectangle.getDimension(); i ++)  
        {  
        	double l1 = this.low.getDoubleCoordinate(i);  
        	double h1 = this.high.getDoubleCoordinate(i);  
        	double l2 = rectangle.low.getDoubleCoordinate(i);  
        	double h2 = rectangle.high.getDoubleCoordinate(i);  
              

            if(l1 <= l2 && h1 <= h2)  
            {  
                ret *= (h1 - l1) - (l2 - l1);  
            }else if(l1 >= l2 && h1 >= h2)  

            {  
                ret *= (h2 - l2) - (l1 - l2);  
            }else if(l1 >= l2 && h1 <= h2)              

            {  
                ret *= h1 - l1;  
            }else if(l1 <= l2 && h1 >= h2)      

            {  
                ret *= h2 - l2;  
            }  
        }  
        return ret;  
    }  
      
    /** 
     * @param rectangle 
     * @return
     */  
    public boolean isIntersection(Rectangle rectangle)  
    {  
        if(rectangle == null)  
            throw new IllegalArgumentException("Rectangle cannot be null.");  
          
          
          
        for(int i = 0; i < getDimension(); i ++)  
        {         	
            if(low.getDoubleCoordinate(i) > rectangle.high.getDoubleCoordinate(i) ||  
                    high.getDoubleCoordinate(i) < rectangle.low.getDoubleCoordinate(i))  
            {  
                return false;  
            }  
        }  
        return true;  
    }  
  
    /** 
     * @return
     */  
    private int getDimension()   
    {  
        return low.getDimension();  
    }    
    
    /**
     * @param rectangle 
     * @return 
     */  
    public boolean enclosure(Rectangle rectangle)   
    {  
        if(rectangle == null)  
            throw new IllegalArgumentException("Rectangle cannot be null.");  
          
        for(int i = 0; i < getDimension(); i ++)  
        {  
            if(rectangle.low.getDoubleCoordinate(i) < low.getDoubleCoordinate(i) ||  
                    rectangle.high.getDoubleCoordinate(i) > high.getDoubleCoordinate(i))  
                return false;  
        }  
        return true;  
    }  
      
    @Override  
    public boolean equals(Object obj)   
    {  
        if(obj instanceof Rectangle)  
        {  
            Rectangle rectangle = (Rectangle) obj;  
            if(low.equals(rectangle.getLow()) && high.equals(rectangle.getHigh()))  
                return true;  
        }  
        return false;  
    }  
}  