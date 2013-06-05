package org.jeo.mongo;

import java.io.IOException;

import org.jeo.TestData;
import org.jeo.data.Query;
import org.jeo.data.VectorData;
import org.jeo.feature.Feature;
import org.jeo.feature.Schema;
import org.jeo.geojson.GeoJSONWriter;
import org.jeo.geom.Geom;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;

public class MongoTestData {

    public void setUp(DBCollection dbcol, MongoWorkspace workspace) throws IOException {
        VectorData data = TestData.states();
        Schema schema = data.getSchema();

        for (Feature f : data.cursor(new Query())) {
            Geometry g = f.geometry();
            g = Geom.iterate((MultiPolygon) f.geometry()).iterator().next();

            DBObject obj = new BasicDBObject(f.map());
            obj.put(schema.geometry().getName(), JSON.parse(GeoJSONWriter.toString(g)));
            dbcol.insert(obj);
        }

        dbcol.ensureIndex(BasicDBObjectBuilder.start().add(
            data.getSchema().geometry().getName(), "2dsphere").get());

        workspace.setMapper(new DefaultMapper(new Mapping().geometry("geometry")));
    }
}