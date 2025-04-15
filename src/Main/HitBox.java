package Main;

public class HitBox {
	
	public int x, y, r, width, height;
	public int xx, yy;
	public String type;
	
	public HitBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xx = x + width;
		this.yy = y + height;
		this.type = "Rectangle";
	}
	
	public HitBox(int x, int y, int r) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.type = "Circle";
	}
	
	public void setX(int x) {
		this.x = x;
		this.xx = x + this.width;
	}
	
	public void setY(int y) {
		this.y = y;
		this.yy = y + this.height;
	}
	
	public void setWidth(int width) {
		this.width = width;
		this.xx = this.x + this.width;
	}
	
	public void setHeight(int height) {
		this.height = height;
		this.yy = this.y + this.height;
	}
	
	public boolean intersect(HitBox p) {
	    if (xx < p.x || x > p.xx || yy < p.y || y > p.yy) {
	        return false;
	    }
	    return true;
	}

	public void setR(int r) {
		this.r = r;
	}
	
	public boolean intersectC(HitBox p) {
		
		int nx = Math.max(p.x, Math.min(this.x, p.xx));
		int ny = Math.max(p.y, Math.min(this.y, p.yy));
		double dist = Math.sqrt((nx - this.x)*(nx - this.x) + (ny - this.y)*(ny - this.y));
		
		return dist <= this.r;
	}

}
