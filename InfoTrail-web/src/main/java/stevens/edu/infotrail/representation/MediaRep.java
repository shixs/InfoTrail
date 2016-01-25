package stevens.edu.infotrail.representation;

import stevens.edu.infotrail.domain.entity.Media;

/**
 *
 * @author Xingsheng
 * @version %I%,%G%
 */
public class MediaRep {
    private int mediaId;
    private String name;
    private String type;
    private String link;
    private int parentId;
    private String description;
    
    private MediaRep(){
        this.mediaId = 0;
        this.name = "";
        this.type = "";
        this.link = "";
        this.parentId = 0;
        this.description = "";
    }
    public MediaRep(Media media){
        this.mediaId = media.getMediaId();
        this.name = (media.getName() != null)?media.getName():"";
        this.type = (media.getType() != null)?media.getType():"";
        this.link = (media.getLink() != null)?media.getLink():"";
        this.parentId = (media.getParentId() != null)?media.getParentId():0;
        this.description = (media.getDescription() != null)?media.getDescription():"";
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
