import org.jfree.data.xy.XYSeries;
import org.uncommons.maths.binary.BitString;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.factories.BitStringFactory;
import org.uncommons.watchmaker.framework.operators.BitStringCrossover;
import org.uncommons.watchmaker.framework.operators.BitStringMutation;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import org.uncommons.watchmaker.swing.evolutionmonitor.EvolutionMonitor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on April, 2019
 *
 * @author yagiz
 */
public class Example {

	private static final int BITS = 100;
	private static final int MAX_GENERATION_NUMBER = 1000;

	public static void main(String[] args) {
		List<XYSeries> xySeries = evolveBits(BITS);
		SwingUtilities.invokeLater(() -> {
			LineChartEx chart1 = new LineChartEx(xySeries.get(0), "Std dev of fitness per time.", "Std dev of every gen. fitness");
			chart1.setVisible(true);
		});
		SwingUtilities.invokeLater(() -> {
			LineChartEx chart2 = new LineChartEx(xySeries.get(1), "Fittests per time.", "Fittests of every gen.");
			chart2.setVisible(true);
		});
		SwingUtilities.invokeLater(() -> {
			LineChartEx chart2 = new LineChartEx(xySeries.get(2), "Mean per time.", "Mean of every gen.");
			chart2.setVisible(true);
		});
	}

	public static List<XYSeries> evolveBits(int length) {
		List<EvolutionaryOperator<BitString>> operators = new ArrayList<EvolutionaryOperator<BitString>>(2);
		operators.add(new BitStringCrossover(1, new Probability(0.7d)));
		operators.add(new BitStringMutation(new Probability(1/Double.valueOf(BITS))));
		EvolutionaryOperator<BitString> pipeline = new EvolutionPipeline<BitString>(operators);
		GenerationalEvolutionEngine<BitString> engine = new GenerationalEvolutionEngine<BitString>(new BitStringFactory(length), pipeline,
																								   new BitStringEvaluator(BITS),
																								   new RouletteWheelSelection(),
																								   new MersenneTwisterRNG());
		//Engine confs.
		EvolutionLoggerObserver evolutionLoggerObserver = new EvolutionLoggerObserver();
		EvolutionLogger<BitString> evolutionLogger= new EvolutionLogger<BitString>(BITS, MAX_GENERATION_NUMBER, evolutionLoggerObserver);
		engine.setSingleThreaded(true); // Performs better for very trivial fitness evaluations.
		engine.addEvolutionObserver(evolutionLogger);
		EvolutionMonitor<BitString> evolutionMonitor = new EvolutionMonitor(true);
		evolutionMonitor.showInFrame("zzz", true);
		engine.addEvolutionObserver(evolutionMonitor);
		engine.evolve(100, // 100 individuals in each generation.
							 0, // Don't use elitism.
							 new GenerationCount(1000), new TargetFitness(length, true)); // Continue until a perfect match is found.
		//XYSeries for plotting.
		List<XYSeries> xySeries = new ArrayList<>();
		xySeries.add(evolutionLoggerObserver.getStdDevXySeries());
		xySeries.add(evolutionLogger.getFittestXySeries());
		xySeries.add(evolutionLogger.getMeanXySeries());
		return xySeries;
	}

}
