package eu.activelogic.android.uone.files;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UONode extends UOBaseNode {


    public static enum Kind {
	FILE,
	DIRECTORY;
    }

    public final Boolean isRoot;
    public String resourcePath;
    public final String parentPath;
    public final String volumePath;
    public String hash;
    public java.util.List<UONode> children;
    public final String publicUrl;
    public final Kind kind;
    public Integer size;
    public final String contentPath;
    public final Boolean hasChildren;
    public Boolean isPublic;
    public final java.util.Date whenCreated;
    public final java.util.Date whenChanged;
    public final Integer generation;
    public final String key;
    public final Integer generationCreated;

    public UONode(org.json.JSONObject in) throws Exception {
	super();
	this.isRoot = safeBoolean(in, "is_root");
	this.resourcePath = safeString(in, "resource_path");
	this.parentPath = safeString(in, "parent_path");
	this.volumePath = safeString(in, "volume_path");
	this.hash = safeString(in, "hash");
	this.publicUrl = safeString(in, "public_url");
	this.kind = safeEnum(Kind.class, in, "kind");
	this.size = safeInteger(in, "size");
	this.contentPath = safeString(in, "content_path");
	this.hasChildren = safeBoolean(in, "has_children");
	this.isPublic = safeBoolean(in, "is_public");
	this.whenCreated = safeDate(in, "when_created");
	this.path = safeString(in, "path");
	this.whenChanged = safeDate(in, "when_changed");
	this.generation = safeInteger(in, "generation");
	this.key = safeString(in, "key");
	this.generationCreated = safeInteger(in, "generation_created");
	if (in.has("children")) {
	    this.children = new ArrayList<UONode>();
	    JSONArray childrenArrayParse = in.getJSONArray("children");
	    for (int i = 0; i < childrenArrayParse.length(); i++) {
		this.children.add(new UONode(childrenArrayParse.getJSONObject(i)));
	    }
	}
	else {
	    this.children = null;
	}
    }

    public JSONObject toJSONObject() throws JSONException {
	JSONObject me = new JSONObject();
	me.put("is_root", this.isRoot);
	me.put("resource_path", this.resourcePath);
	me.put("parent_path", this.parentPath);
	me.put("volume_path", this.volumePath);
	me.put("hash", this.hash);
	if (this.children != null) {
	    List<JSONObject> objects = new ArrayList<JSONObject>();
	    for (UONode item : this.children) {
		objects.add(item.toJSONObject());
	    }
	    me.put("children", objects);
	}
	me.put("public_url", this.publicUrl);
	me.put("kind", this.kind != null ? this.kind.toString() : null);
	me.put("size", this.size);
	me.put("content_path", this.contentPath);
	me.put("has_children", this.hasChildren);
	me.put("is_public", this.isPublic);
	me.put("when_created", formatDate(this.whenCreated));
	me.put("path", this.path);
	me.put("when_changed", formatDate(this.whenChanged));
	me.put("generation", this.generation);
	me.put("key", this.key);
	me.put("generation_created", this.generationCreated);

	return me;
    }


    public String toString() {

	StringBuilder sb = new StringBuilder();

	sb.append("[").append(kind).append(" = ").append(this.resourcePath).append("; ").append(children).append("]");

	return sb.toString();

    }

}