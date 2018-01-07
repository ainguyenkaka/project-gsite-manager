package com.gsite.app.config.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.util.Date;

@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(author = "initiator", id = "01-addTemplates", order = "01")
    public void addTemplates(DB db) {
        DBCollection templateCollection = db.getCollection("web_template");
        templateCollection.createIndex("name");
        templateCollection.insert(BasicDBObjectBuilder
            .start("_id", "template-1")
            .add("name", "The basic one")
            .add("source", "basic-template")
            .add("category", "sport")
            .add("price", 0)
            .add("image", "mainImage")
            .add("created", new Date())
            .get());
        templateCollection.insert(BasicDBObjectBuilder
            .start("_id", "template-2")
            .add("name", "The latest one")
            .add("source", "latest-template")
            .add("category", "sport")
            .add("price", 0)
            .add("image", "mainImage")
            .add("created", new Date())
            .get());
        templateCollection.insert(BasicDBObjectBuilder
            .start("_id", "template-3")
            .add("name", "The beauty")
            .add("source", "beautiful-template")
            .add("category", "sport")
            .add("price", 5)
            .add("image", "mainImage")
            .add("created", new Date())
            .get());

    }

    @ChangeSet(author = "initiator", id = "01-addQuestions", order = "02")
    public void addQuestions(DB db) {
        DBCollection templateCollection = db.getCollection("question");
        templateCollection.createIndex("name");
        templateCollection.insert(BasicDBObjectBuilder
            .start("_id", "question-1")
            .add("content", "How many templates GSite has now?")
            .add("answer", "There are three templates.")
            .add("user_id", "user-5")
            .add("is_frequent", true)
            .add("created", new Date())
            .get());
        templateCollection.insert(BasicDBObjectBuilder
            .start("_id", "question-2")
            .add("content", "Why GSite system still in Beta?")
            .add("answer", "It is not stable and we are looking forward to your feedbacks.")
            .add("user_id", "user-5")
            .add("is_frequent", true)
            .add("created", new Date())
            .get());
        templateCollection.insert(BasicDBObjectBuilder
            .start("_id", "question-3")
            .add("content", "Is payment transaction safe?")
            .add("answer", "All information are encrypted safely.")
            .add("user_id", "user-5")
            .add("is_frequent", true)
            .add("created", new Date())
            .get());

    }

}
