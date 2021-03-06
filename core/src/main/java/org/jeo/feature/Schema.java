package org.jeo.feature;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.osgeo.proj4j.CoordinateReferenceSystem;

/**
 * Describes the structure of a {@link Feature} object.
 * <p>
 * A schema is am immutable collection of named {@link Field} objects, any of which may be a 
 * geometry field. 
 * </p>
 * @author Justin Deoliveira, OpenGeo
 */
public class Schema implements Iterable<Field> {

    /** schema name */
    String name;

    /** list of fields */
    List<Field> fields;

    /**
     * Returns a new schema builder.
     * 
     * @param name The name of the schema.
     */
    public static SchemaBuilder build(String name) {
        return new SchemaBuilder(name);
    }

    /**
     * Constructs a new Schema.
     * 
     * @param name Name of the schema.
     * @param fields List of fields 
     */
    public Schema(String name, List<Field> fields) {
        this.name = name;
        this.fields = Collections.unmodifiableList(fields);
    }

    /**
     * Name of the schema.
     */
    public String getName() {
        return name;
    }

    /**
     * Derived geometry field of the schema.
     * <p>
     * This method returns the first field that returns true from {@link Field#isGeometry()}. If 
     * no such field is found <code>null</code> is returned.
     * </p>
     */
    public Field geometry() {
        //TODO: store the derived result
        for (Field f : this) {
            if (f.isGeometry()) {
                return f;
            }
        }
        return null;
    }

    /**
     * Derived coordinate reference system for the schema.
     * <p>
     * This method delegates to <code>geometry().getCRS()</code>.
     * </p> 
     * @return The coordinate reference system object, or <code>null</code> if not available or 
     * no geometry field exists.
     */
    public CoordinateReferenceSystem crs() {
        Field g = geometry();
        return g != null ? g.getCRS() : null;
    }

    /**
     * The immutable list of fields for the schema.
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * Returns the field of the schema with the specified name, or <code>null</code> if no such 
     * field exists.
     * 
     * @param name The field name.
     */
    public Field field(String name) {
        int i = indexOf(name);
        return i != -1 ? fields.get(i) : null;
    }

    /**
     * Returns the index of the field in the schema with the specified name, or <code>-1</code> if
     * no such field exists.
     * 
     * @param name The field name.
     * 
     * @return The index position, or <code>-1</code>
     */
    public int indexOf(String name) {
        //TODO: potentially add an index of name to field
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            if (f.getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Number of fields in the schema.
     */
    public int size() {
        return fields.size();
    }

    /**
     * Iterator over the fields of the schema.
     */
    @Override
    public Iterator<Field> iterator() {
        return fields.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name).append("[");
        if (!fields.isEmpty()) {
            for (Field f : fields) {
                sb.append(f).append(",");
            }
            sb.setLength(sb.length()-1);
        }
        return sb.toString();
    }
}
