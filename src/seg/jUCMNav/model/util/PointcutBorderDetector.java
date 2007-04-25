package seg.jUCMNav.model.util;

import java.util.Iterator;
import java.util.List;

import ucm.map.EndPoint;
import ucm.map.PathNode;
import ucm.map.PluginBinding;
import ucm.map.StartPoint;
import ucm.map.Stub;
import ucm.map.UCMmap;
import urncore.IURNNode;
import urncore.UCMmodelElement;

/**
 * given a PathNode, checks whether the path node is a border element for a pointcut expression
 * (only the following qualify: unnamed start/end point on a UCM that is plugged into at 
 * least one pointcut stub and into no normal stubs)
 * @author gunterm
 */
public class PointcutBorderDetector {

	/**
	 * @param node for which to check
	 * @return true if the node is a border element for a pointcut expression; false otherwise
	 */
	public static boolean detectPointcutBorder(PathNode node) {
		boolean isPointcutBorder = false;
		if ((node instanceof StartPoint || node instanceof EndPoint)) {
			// only unnamed start or end point can be used as a border for a pointcut expression
			if (((UCMmodelElement) node).getName().trim().equals("")) { //$NON-NLS-1$
	    		List bindings = ((UCMmap) ((IURNNode) node).getDiagram()).getParentStub();
	    		boolean pluggedIntoPointcutStub = false;
	    		boolean pluggedIntoNormalStub = false;
	    		if (bindings != null) {
	    			for (Iterator iter = bindings.iterator(); iter.hasNext();) {
	    				PluginBinding binding = (PluginBinding) iter.next();
	    				Stub stub = binding.getStub(); 
	    				if (stub != null) {
	    					if (stub.isPointcut())
	    						pluggedIntoPointcutStub = true;
	    					else
	    						pluggedIntoNormalStub = true;
	    				}
	    			}
	    		}
	    		// node must be plugged into at least one pointcut stub and no normal stubs
	    		isPointcutBorder = pluggedIntoPointcutStub && !pluggedIntoNormalStub;
	    	}
	    }
		return isPointcutBorder;
	}

}