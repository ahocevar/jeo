package org.jeo.geopkg;

import java.util.Date;

import com.vividsolutions.jts.geom.Envelope;

/**
 * Entry in a geopackage.
 * <p>
 * This class corresponds to the "geopackage_contents" table.
 * </p>
 * @author Justin Deoliveira, OpenGeo
 *
 */
public class Entry {

    public static enum DataType {
        Feature("features"), Raster("rasters"), Tile("tiles"), 
        FeatureWithRaster("featuresWithRasters");

        String value;
        DataType(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    protected String tableName;
    protected DataType dataType;
    protected String identifier;
    protected String description;
    protected Date lastChange;
    protected Envelope bounds;
    protected Integer srid;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public Envelope getBounds() {
        return bounds;
    }

    public void setBounds(Envelope bounds) {
        this.bounds = bounds;
    }

    public Integer getSrid() {
        return srid;
    }

    public void setSrid(Integer srid) {
        this.srid = srid;
    }

    void init(Entry e) {
        setDescription(e.getDescription());
        setIdentifier(e.getIdentifier());
        setDataType(e.getDataType());
        setBounds(e.getBounds());
        setSrid(e.getSrid());
        setTableName(e.getTableName());
    }

    Entry copy() {
        Entry e = new Entry();
        e.init(this);
        return e;
    }
}
