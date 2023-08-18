/*
 *	===============================================================================
 *	OvalShape.java : A shape that is an oval.
 *  YOUR UPI: bkwo461 - Shinbeom Kwon
 *	=============================================================================== */
import java.awt.*;
class OvalShape extends Shape {
	public OvalShape() {}
	public OvalShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt) {
		super(x ,y ,w, h ,mw ,mh, c, pt);
	}
	public OvalShape(int x, int y, int w, int h, int mw, int mh, Color c, PathType pt, String text) {
		super(x ,y ,w, h ,mw ,mh, c, pt, text);
	}
	@Override
	public void draw(Graphics g) {
		//complete this
		g.setColor(super.getColor());
		g.fillOval(this.x, this.y, this.width, this.height);
	}
	@Override
	public boolean contains(Point mousePt) {
		double dx, dy;
		Point EndPt = new Point(x + width, y + height);
		dx = (2 * mousePt.x - x - EndPt.x) / (double) width;
		dy = (2 * mousePt.y - y - EndPt.y) / (double) height;
		return dx * dx + dy * dy < 1.0;
	}
}