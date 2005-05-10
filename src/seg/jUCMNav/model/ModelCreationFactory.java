/*
 * Created on 2005-01-30
 *
 */
package seg.jUCMNav.model;

import org.eclipse.gef.requests.CreationFactory;

import seg.jUCMNav.model.commands.changeConstraints.SetConstraintComponentRefCommand;
import ucm.UcmFactory;
import ucm.map.AndFork;
import ucm.map.ComponentRef;
import ucm.map.EmptyPoint;
import ucm.map.EndPoint;
import ucm.map.MapFactory;
import ucm.map.NodeConnection;
import ucm.map.OrFork;
import ucm.map.RespRef;
import ucm.map.StartPoint;
import urn.URNspec;
import urn.UrnFactory;
import urncore.Component;
import urncore.ComponentKind;
import urncore.ComponentLabel;
import urncore.NodeLabel;
import urncore.UrncoreFactory;

/**
 * Created on 2005-01-30
 * 
 * This class implements the CreationFactory to be used as the central point to obtain new model elements. It sets up the default values for all new elements.
 * It in turn uses the EMF-generated factories to create the model instances
 * 
 * Our application will use the static getNewObject methods to access the factories. 
 * The palette needs to be passed a CreationFactory; that is the reason of the non-static methods.   
 * @author ddean
 *  
 */
public class ModelCreationFactory implements CreationFactory {
    private Class targetClass;
    private int type;

    /**
     * @param targetClass
     *            The class we need to create from this factory.
     */
    public ModelCreationFactory(Class targetClass) {
        this.targetClass = targetClass;
        this.type = 0;
    }

    /**
     * 
     * @param targetClass
     *            The class we need to create from this factory.
     * @param type
     *            If this is a ComponentRef, we can pass the ComponentKind.
     */
    public ModelCreationFactory(Class targetClass, int type) {
        this.targetClass = targetClass;
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.requests.CreationFactory#getObjectType()
     */
    public Object getObjectType() {
        return targetClass;
    }


    public Object getNewObject() {
        return getNewObject(targetClass, type);
    }

    /**
     * Equivalent to getNewObject(targetClass, 0);
     * @param targetClass the class to obtain a new instance of
     * @return
     */
    public static Object getNewObject(Class targetClass) {
        return getNewObject(targetClass, 0);
    }

    /**
     * Returns a new model element preset with its default values. Note that no exception will be thrown for unknown classes but there will be a message printed
     * on the standard output to facilitate debugging for new developers.
     * 
     * @see org.eclipse.gef.requests.CreationFactory#getNewObject()
     */    
    public static Object getNewObject(Class targetClass, int type) {
        MapFactory factory = MapFactory.eINSTANCE;

        Object result = null;

        if (targetClass != null) {
            if (targetClass.equals(URNspec.class)) {
                // create the URN spec
                URNspec urnspec = UrnFactory.eINSTANCE.createURNspec();

                // add its URN definition
                urnspec.setUrndef(UrncoreFactory.eINSTANCE.createURNdefinition());

                // add its UCMspec
                urnspec.setUcmspec(UcmFactory.eINSTANCE.createUCMspec());

                // create a map
                ucm.map.Map ucm = factory.createMap();

                // add an empty pathgraph to this map
                ucm.setPathGraph(factory.createPathGraph());

                // add the new mapp to the UCMspec
                urnspec.getUcmspec().getMaps().add(ucm);

                result = urnspec;

            } else if (targetClass.equals(EmptyPoint.class)) {
                result = factory.createEmptyPoint();
            } else if (targetClass.equals(NodeConnection.class)) {
                result = factory.createNodeConnection();
            } else if (targetClass.equals(RespRef.class)) {
                result = factory.createRespRef();
            } else if (targetClass.equals(StartPoint.class)) {
                result = factory.createStartPoint();
            } else if (targetClass.equals(EndPoint.class)) {
                result = factory.createEndPoint();
            } else if (targetClass.equals(NodeLabel.class)) {
            	UrncoreFactory urncoreFactory = UrncoreFactory.eINSTANCE;
            	result = urncoreFactory.createNodeLabel();
            } else if (targetClass.equals(ComponentLabel.class)) {
            	UrncoreFactory urncoreFactory = UrncoreFactory.eINSTANCE;
            	result = urncoreFactory.createComponentLabel();
            } else if (targetClass.equals(ComponentRef.class)) {

                // create the component ref
                result = factory.createComponentRef();

                // new component refs must have a component definition
                Component compdef = UrncoreFactory.eINSTANCE.createComponent();
                ((ComponentRef) result).setCompDef(compdef);

                // define the ComponentKind according to what was set in the construction
                compdef.setKind(ComponentKind.get(type));
                ((ComponentRef)result).setHeight(SetConstraintComponentRefCommand.DEFAULT_HEIGHT);
                ((ComponentRef)result).setWidth(SetConstraintComponentRefCommand.DEFAULT_WIDTH);
            } else if (targetClass.equals(OrFork.class)){
                result = factory.createOrFork();
            } else if (targetClass.equals(AndFork.class)) {
            	result = factory.createAndFork();
            } else {
                System.out.println("Unknown class passed to ModelCreationFactory");
            }
        }
        return result;

    }

}