package seg.jUCMNav.figures;

import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * This figure represent a timer in the model.
 * 
 * @author jkealey, jmcmanus
 */
public class TimerFigure extends PathNodeFigure {

    /**
     * @return Returns the default dimension.
     */
    public static Dimension getDefaultDimension() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /** The empty point is an ellipse. */
    private Ellipse ellipse;
    private Polyline poly;

    public TimerFigure() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see seg.jUCMNav.figures.PathNodeFigure#createFigure()
     */
    protected void createFigure() {
        ellipse = new Ellipse();
        ellipse.setBounds(new Rectangle(preferredSize.width / 8, preferredSize.height / 8, DEFAULT_WIDTH * 3 / 4, DEFAULT_HEIGHT * 3 / 4));
        poly = new Polyline();
        poly.addPoint(new Point(DEFAULT_WIDTH / 2, preferredSize.height / 8));
        poly.addPoint(new Point(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2));
        poly.addPoint(new Point(preferredSize.width * 7 / 8, DEFAULT_HEIGHT / 2));
        ellipse.add(poly);
        ellipse.setLineWidth(2);
        poly.setLineWidth(2);
        
        add(ellipse);

    }

    /*
     * (non-Javadoc)
     * 
     * @see seg.jUCMNav.figures.PathNodeFigure#getFigure()
     */
    public Figure getFigure() {
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see seg.jUCMNav.figures.PathNodeFigure#initAnchor()
     */
    protected void initAnchor() {
        incomingAnchor = new EllipseAnchor(this);
        outgoingAnchor = new EllipseAnchor(this);
    }


    protected boolean useLocalCoordinates() {
        return true;
    }
}