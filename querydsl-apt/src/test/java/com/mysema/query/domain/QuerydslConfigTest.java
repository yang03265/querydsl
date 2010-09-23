/*
 * Copyright (c) 2009 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.query.domain;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.mysema.query.annotations.QueryEntity;
import com.mysema.query.annotations.Config;

public class QuerydslConfigTest {

    @Config(entityAccessors=true)
    @QueryEntity
    public static class Superclass{

        Entity prop3;
    }

    @Config(entityAccessors=true, listAccessors = true, mapAccessors= true)
    @QueryEntity
    public static class Entity extends Superclass{

        Entity prop1;

        Entity prop2;

        List<Entity> entityList;

        Map<String,Entity> entityMap;
    }

    @Test
    public void test_long_path(){
        assertEquals("entity.prop1.prop2.prop1", QQuerydslConfigTest_Entity.entity.prop1().prop2().prop1().toString());
    }

}
