package com.pindiboy.playit.model.bean.youtube;

/**
 * Created by Jiangwenjin on 2017/3/16.
 * <p>
 * "duration": "PT35M52S",
 * "dimension": "2d",
 * "definition": "sd",
 * "caption": "false",
 * "licensedContent": false,
 * "projection": "rectangular"
 */

public class ContentDetail {
    private String caption;
    private String definition;
    private String dimension;
    private String duration;
    private boolean licensedContent;
    private String projection;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isLicensedContent() {
        return licensedContent;
    }

    public void setLicensedContent(boolean licensedContent) {
        this.licensedContent = licensedContent;
    }

    public String getProjection() {
        return projection;
    }

    public void setProjection(String projection) {
        this.projection = projection;
    }
}
