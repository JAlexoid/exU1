package eu.activelogic.android.uone.files;

public class UOBaseNode extends UOBase {

    public String path;
    
    public UOBaseNode() {}
    
    public UOBaseNode(String path) {
	this.path = path;
    }
    
    public String getName(){
	return path != null ? path.replaceAll("\\/\\~\\/", "").replaceAll("\\.ubuntuone\\/", "U1 - ") : null;
    }
    
    public String toString(){
	return getName();
    }
    
}
