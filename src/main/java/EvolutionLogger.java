import javafx.util.Pair;
import org.jfree.data.xy.XYSeries;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created on April, 2019
 *
 * @author yagiz
 */
public class EvolutionLogger <T> extends Observable implements EvolutionObserver<T> {

	private int bitSize;
	private int maxGeneration;
	private XYSeries xySeriesBests;
	private XYSeries xySeriesMeans;
	private List<Pair<Double,Long>> bestAndDurationPairList;
	private List<Pair<Double,Long>> meanAndDurationPairList;


	public EvolutionLogger(int bitsize, int maxGeneration) {
		this.bitSize = bitsize;
		this.maxGeneration = maxGeneration-1;
	}

	public EvolutionLogger(int bitsize, int maxGeneration, Observer observer) {
		this.bitSize = bitsize;
		this.maxGeneration = maxGeneration-1;
		addObserver(observer);
		this.bestAndDurationPairList = new ArrayList<>();
		this.meanAndDurationPairList = new ArrayList<>();
	}

	public void populationUpdate(PopulationData<? extends T> data)
	{
		System.out.println("Generation " + data.getGenerationNumber() + ": " + data.getBestCandidateFitness() +" Candidate: "
								   + data.getBestCandidate() + " Taken time: " + data.getElapsedTime());
		setChanged();
		bestAndDurationPairList.add(new Pair<Double, Long>(data.getBestCandidateFitness(), data.getElapsedTime()));
		meanAndDurationPairList.add(new Pair<Double, Long>(data.getMeanFitness(), data.getElapsedTime()));
		notifyObservers(new Pair<Double, Long>(data.getFitnessStandardDeviation(), data.getElapsedTime()));
		if ((data.getBestCandidateFitness() == bitSize) || (data.getGenerationNumber() == maxGeneration)) {
			setChanged();
			notifyObservers();
			setXYSeries();
		}
		data.toString();
	}

	public void islandPopulationUpdate(int islandIndex, PopulationData<? extends T> populationData)
	{
		// Do nothing.
	}

	private void setXYSeries() {
		XYSeries xySeriesStdDev = new XYSeries("Graph");
		XYSeries xySeriesMeans = new XYSeries("Graph");
		bestAndDurationPairList.forEach(pair -> xySeriesStdDev.add(pair.getValue(), pair.getKey()));
		meanAndDurationPairList.forEach(pair -> xySeriesMeans.add(pair.getValue(), pair.getKey()));
		//		XYSeries xySeriesBests = new XYSeries("Graph");
		//		bestAndDurationPairList.forEach(pair -> xySeriesBests.add(pair.getValue(), pair.getKey()));
		this.xySeriesBests = xySeriesStdDev;
		this.xySeriesMeans = xySeriesMeans;
		//		this.xySeriesBests = xySeriesBests;
	}

	public XYSeries getFittestXySeries() {
		return xySeriesBests;
	}

	public XYSeries getMeanXySeries() {
		return xySeriesMeans;
	}

}
