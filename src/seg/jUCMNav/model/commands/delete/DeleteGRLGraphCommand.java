/**
 * 
 */
package seg.jUCMNav.model.commands.delete;

import grl.GRLGraph;

import org.eclipse.gef.commands.CompoundCommand;

import seg.jUCMNav.model.commands.delete.internal.CleanRelationshipsCommand;
import seg.jUCMNav.model.commands.delete.internal.DeleteGRLGraphRefDefLinksCommand;
import urncore.IURNDiagram;


/**
 * Command to delete a GRLGraph. 
 * @author Jean-Fran�ois Roy
 *
 */
public class DeleteGRLGraphCommand extends CompoundCommand {

    private IURNDiagram diagram;
    
    /**
     * 
     */
    public DeleteGRLGraphCommand(GRLGraph diagram) {
        setLabel("Delete GRLGraph");
        setDiagram(diagram);
        
        add(new CleanRelationshipsCommand(diagram));
        //Command to delete information about link between GRL and UCM should be add in the CleanRelationshipCommand
        add(new DeleteGRLGraphRefDefLinksCommand(diagram));
    }

    public IURNDiagram getDiagram() {
        return diagram;
    }

    public void setDiagram(IURNDiagram diagram) {
        this.diagram = diagram;
    }
    
    
}