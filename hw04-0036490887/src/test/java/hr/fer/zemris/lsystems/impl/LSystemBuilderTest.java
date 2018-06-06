package hr.fer.zemris.lsystems.impl;

import org.junit.*;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.impl.parser.exceptions.AxiomException;
import hr.fer.zemris.lsystems.impl.parser.Parser;
import hr.fer.zemris.lsystems.impl.parser.exceptions.OriginException;
import hr.fer.zemris.lsystems.impl.parser.exceptions.ProductionException;
import hr.fer.zemris.lsystems.impl.parser.exceptions.UnitLengthDegreeScalerException;
import hr.fer.zemris.lsystems.impl.parser.exceptions.UnitLengthException;

public class LSystemBuilderTest {

	private static final double DELTA = 1e-4;

	@Test
	public void testOrigin() {
		String[] data = new String[1];
		data[0] = "origin 0.1 0.4";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		LSystemBuilderImpl builder = (LSystemBuilderImpl) provider.createLSystemBuilder().configureFromText(data);
		Assert.assertEquals(0.1, builder.getTurtleState().getPosition().getX(), DELTA);
		Assert.assertEquals(0.4, builder.getTurtleState().getPosition().getY(), DELTA);
	}

	@Test
	public void testAngle() {
		String[] data = new String[1];
		data[0] = "angle 90";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		LSystemBuilderImpl builder = (LSystemBuilderImpl) provider.createLSystemBuilder().configureFromText(data);
		Assert.assertEquals(0, builder.getTurtleState().getDirection().getX(), DELTA);
		Assert.assertEquals(1, builder.getTurtleState().getDirection().getY(), DELTA);
	}

	@Test
	public void testUnitLength() {
		String[] data = new String[1];
		data[0] = "unitLength 18";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		LSystemBuilderImpl builder = (LSystemBuilderImpl) provider.createLSystemBuilder().configureFromText(data);
		Assert.assertEquals(18, builder.getTurtleState().getStep(), DELTA);
	}

	@Test
	public void testProduction() {
		String[] data = new String[1];
		data[0] = "production F F+-AB";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		String result = provider.createLSystemBuilder().configureFromText(data).build().generate(0);
		Assert.assertEquals(0, result.compareTo(""));

	}

	@Test
	public void testAxiom() {
		String[] data = new String[1];
		data[0] = "axiom F+-AB";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		String result = provider.createLSystemBuilder().configureFromText(data).build().generate(0);
		Assert.assertEquals(0, result.compareTo("F+-AB"));

	}

	@Test
	public void testProductionLevelOne() {
		String[] data = new String[2];
		data[0] = "axiom F";
		data[1] = "production F F+";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		String result = provider.createLSystemBuilder().configureFromText(data).build().generate(1);

		Assert.assertEquals(0, result.compareTo("F+ "));

	}

	@Test
	public void testProductionLevelTwo() {
		String[] data = new String[2];
		data[0] = "axiom F";
		data[1] = "production F F+";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		String result = provider.createLSystemBuilder().configureFromText(data).build().generate(2);
		Assert.assertEquals(0, result.compareTo("F+ + "));

	}

	@Test
	public void testProductionLevelThree() {
		String[] data = new String[2];
		data[0] = "axiom F";
		data[1] = "production F F+";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		String result = provider.createLSystemBuilder().configureFromText(data).build().generate(3);
		Assert.assertEquals(0, result.compareTo("F+ + + "));

	}

	// ---------------------------------------
	@Test
	public void testAxiomException() {
		String[] data = new String[1];
		data[0] = "axiom ";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		provider.createLSystemBuilder().configureFromText(data);

	}

	@Test
	public void testOriginException() {
		String[] data = new String[1];
		data[0] = "origin F F+-AB";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		provider.createLSystemBuilder().configureFromText(data);
	}

	@Test
	public void testProductionException() {
		String[] data = new String[1];
		data[0] = "production F";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		provider.createLSystemBuilder().configureFromText(data);

	}

	@Test
	public void testUnitLengthException() {
		String[] data = new String[1];
		data[0] = "unitLength mirko";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		provider.createLSystemBuilder().configureFromText(data);

	}

	@Test
	public void testUnitLengthDegreeScalerExceptionDivideSign() {
		String[] data = new String[1];
		data[0] = "unitLengthDegreeScaler 14.0 /15.0";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		String result = provider.createLSystemBuilder().configureFromText(data).build().generate(0);
		Assert.assertEquals(0, result.compareTo(""));

	}

	@Test
	public void testUnitLengthDegreeScalerExceptionParseException() {
		String[] data = new String[1];
		data[0] = "unitLengthDegreeScaler 14.0 / 15a.0";
		LSystemBuilderProvider provider = LSystemBuilderImpl::new;
		String result = provider.createLSystemBuilder().configureFromText(data).build().generate(0);
		Assert.assertEquals(0, result.compareTo(""));

	}
}
