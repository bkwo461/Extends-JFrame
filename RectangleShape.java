/*
 *	===============================================================================
 *	RectangleShape.java : A shape that is a rectangle.
 *  YOUR UPI:bkwo461 - Shinbeom Kwon
 *	=============================================================================== */
import java.awt.*;
class RectangleShape extends Shape {
    public RectangleShape() {}
	public RectangleShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt) {
		super(x ,y ,w, h ,mw ,mh, c, pt);
	}
	public RectangleShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt,String text) {
		super(x ,y ,w, h ,mw ,mh, c, pt, text);
	}
	@Override
	public void draw(Graphics g) {
		//complete this
		g.setColor(super.getColor());
		g.fillRect(this.x, this.y, this.width, this.height);
		
	}
	@Override
	public boolean contains(Point mousePt) {
		return (x <= mousePt.x && mousePt.x <= (x + width + 1)	&&	y <= mousePt.y && mousePt.y <= (y + height + 1));
	}
}