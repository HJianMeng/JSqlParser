package net.sf.jsqlparser.util;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.Select;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author toben
 */
public class SelectUtilsTest {

	public SelectUtilsTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of addColumn method, of class SelectUtils.
	 */
	@Test
	public void testAddExpr() throws JSQLParserException {
		Select select = (Select) CCJSqlParserUtil.parse("select a from mytable");
		SelectUtils.addExpression(select, new Column("b"));
		assertEquals("SELECT a, b FROM mytable", select.toString());
		
		Addition add = new Addition();
		add.setLeftExpression(new LongValue(5));
		add.setRightExpression(new LongValue(6));
		SelectUtils.addExpression(select, add);
		
		assertEquals("SELECT a, b, 5 + 6 FROM mytable", select.toString());
	}
	
	@Test
	public void testAddJoin() throws JSQLParserException {
		Select select = (Select)CCJSqlParserUtil.parse("select a from mytable");
		final EqualsTo equalsTo = new EqualsTo();
		equalsTo.setLeftExpression(new Column("a"));
		equalsTo.setRightExpression(new Column("b"));
		Join addJoin = SelectUtils.addJoin(select, new Table("mytable2"), equalsTo);
		addJoin.setLeft(true);
		assertEquals("SELECT a FROM mytable LEFT JOIN mytable2 ON a = b", select.toString());
	}
}