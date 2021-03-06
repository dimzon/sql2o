package org.sql2o.tools;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * User: dimzon
 * Date: 4/9/14
 * Time: 4:44 AM
 */
public class NamedParameterTest extends TestCase {

    private SqlParameterParsingStrategy sqlParameterParsingStrategy = new DefaultSqlParameterParsingStrategy();

    /*
     A type cast specifies a conversion from one data type to another.
     PostgreSQL accepts two equivalent syntaxes for type casts:
     CAST ( expression AS type )
     expression::type
     */
    public void testPostgresSqlCastSyntax() throws Exception {
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        String preparedQuery = sqlParameterParsingStrategy.parseSql("select :foo", map);
        assertEquals("select ?", preparedQuery);
        assertThat(map.size(), is(equalTo(1)));
        assertThat(map.get("foo").size(), is(equalTo(1)));
        assertThat(map.get("foo").get(0), is(equalTo(1)));

        map.clear();
        preparedQuery = sqlParameterParsingStrategy.parseSql("select (:foo)::uuid", map);
        assertEquals("select (?)::uuid", preparedQuery);
    }

    public void testStringConstant() throws Exception {
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        String preparedQuery = sqlParameterParsingStrategy.parseSql("select ':foo'", map);
        assertEquals("select ':foo'", preparedQuery);
    }
}
